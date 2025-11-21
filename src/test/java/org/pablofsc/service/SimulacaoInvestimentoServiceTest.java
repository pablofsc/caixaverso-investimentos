package org.pablofsc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;
import org.pablofsc.domain.response.SimulacaoInvestimentoResponse;
import org.pablofsc.service.helper.SimulacaoOrchestrator;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SimulacaoInvestimentoServiceTest {

  private ValidacaoSimulacaoService validacaoService;
  private SimulacaoOrchestrator orchestrator;
  private SimulacaoInvestimentoService simulacaoInvestimentoService;

  @BeforeEach
  void setUp() {
    validacaoService = mock(ValidacaoSimulacaoService.class);
    orchestrator = mock(SimulacaoOrchestrator.class);
    simulacaoInvestimentoService = new SimulacaoInvestimentoService(validacaoService, orchestrator);
  }

  @Test
  void testSimularInvestimentoSucesso() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(
        1L, 10000.0, 12, "CDB");

    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .build();

    ProdutoEntity produtoEntity = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Pós-Fixado")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    Produto produtoModel = new Produto(
        1L, "CDB Pós-Fixado", TipoProdutoEnum.CDB, 0.10, NivelRiscoEnum.BAIXO);

    Simulacao simulacao = new Simulacao(11000.0, 0.10, 12);

    ZonedDateTime dataSimulacao = ZonedDateTime.now();

    when(orchestrator.validarEObterCliente(1L)).thenReturn(cliente);
    when(orchestrator.recomendarEValidarProduto(cliente, "CDB", 12)).thenReturn(produtoEntity);
    when(orchestrator.converterProdutoParaModelo(produtoEntity)).thenReturn(produtoModel);
    when(orchestrator.calcularResultado(10000.0, produtoEntity, 12)).thenReturn(simulacao);
    when(orchestrator.obterDataSimulacao()).thenReturn(dataSimulacao);

    // Act
    SimulacaoInvestimentoResponse response = simulacaoInvestimentoService.simularInvestimento(request);

    // Assert
    assertNotNull(response);
    assertEquals(produtoModel, response.getProdutoValidado());
    assertEquals(simulacao, response.getResultadoSimulacao());
    assertEquals(dataSimulacao, response.getDataSimulacao());

    verify(validacaoService).validar(request);
    verify(orchestrator).validarEObterCliente(1L);
    verify(orchestrator).recomendarEValidarProduto(cliente, "CDB", 12);
    verify(orchestrator).converterProdutoParaModelo(produtoEntity);
    verify(orchestrator).calcularResultado(10000.0, produtoEntity, 12);
    verify(orchestrator).obterDataSimulacao();
    verify(orchestrator).construirSimulacaoEntity(eq(cliente), eq(produtoEntity), eq(10000.0), eq(simulacao),
        eq(dataSimulacao));
    verify(orchestrator).persistirSimulacao(any());
  }

  @Test
  void testSimularInvestimentoComValidacaoFalha() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(
        1L, -1000.0, 12, "CDB");

    doThrow(new IllegalArgumentException("Valor deve ser positivo"))
        .when(validacaoService).validar(request);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> simulacaoInvestimentoService.simularInvestimento(request));

    verify(validacaoService).validar(request);
    verifyNoInteractions(orchestrator);
  }

  @Test
  void testSimularInvestimentoComClienteNaoEncontrado() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(
        999L, 10000.0, 12, "CDB");

    doThrow(new RuntimeException("Cliente não encontrado"))
        .when(orchestrator).validarEObterCliente(999L);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> simulacaoInvestimentoService.simularInvestimento(request));

    verify(validacaoService).validar(request);
    verify(orchestrator).validarEObterCliente(999L);
  }

  @Test
  void testSimularInvestimentoComProdutoNaoEncontrado() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(
        1L, 10000.0, 12, "PRODUTO_INVALIDO");

    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .build();

    when(orchestrator.validarEObterCliente(1L)).thenReturn(cliente);
    doThrow(new RuntimeException("Produto não encontrado"))
        .when(orchestrator).recomendarEValidarProduto(cliente, "PRODUTO_INVALIDO", 12);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> simulacaoInvestimentoService.simularInvestimento(request));

    verify(validacaoService).validar(request);
    verify(orchestrator).validarEObterCliente(1L);
    verify(orchestrator).recomendarEValidarProduto(cliente, "PRODUTO_INVALIDO", 12);
  }

  @Test
  void testSimularInvestimentoComFundo() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(
        2L, 5000.0, 6, "FUNDO");

    ClienteEntity cliente = ClienteEntity.builder()
        .id(2L)
        .nome("Maria Santos")
        .build();

    ProdutoEntity produtoEntity = ProdutoEntity.builder()
        .id(2L)
        .nome("Fundo Multimercado")
        .tipo(TipoProdutoEnum.FUNDO)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    Produto produtoModel = new Produto(
        2L, "Fundo Multimercado", TipoProdutoEnum.FUNDO, 0.12, NivelRiscoEnum.ALTO);

    Simulacao simulacao = new Simulacao(5300.0, 0.12, 6);

    ZonedDateTime dataSimulacao = ZonedDateTime.now();

    when(orchestrator.validarEObterCliente(2L)).thenReturn(cliente);
    when(orchestrator.recomendarEValidarProduto(cliente, "FUNDO", 6)).thenReturn(produtoEntity);
    when(orchestrator.converterProdutoParaModelo(produtoEntity)).thenReturn(produtoModel);
    when(orchestrator.calcularResultado(5000.0, produtoEntity, 6)).thenReturn(simulacao);
    when(orchestrator.obterDataSimulacao()).thenReturn(dataSimulacao);

    // Act
    SimulacaoInvestimentoResponse response = simulacaoInvestimentoService.simularInvestimento(request);

    // Assert
    assertNotNull(response);
    assertEquals(produtoModel, response.getProdutoValidado());
    assertEquals(simulacao, response.getResultadoSimulacao());
    assertEquals(dataSimulacao, response.getDataSimulacao());

    verify(validacaoService).validar(request);
    verify(orchestrator).validarEObterCliente(2L);
    verify(orchestrator).recomendarEValidarProduto(cliente, "FUNDO", 6);
    verify(orchestrator).converterProdutoParaModelo(produtoEntity);
    verify(orchestrator).calcularResultado(5000.0, produtoEntity, 6);
    verify(orchestrator).obterDataSimulacao();
    verify(orchestrator).construirSimulacaoEntity(eq(cliente), eq(produtoEntity), eq(5000.0), eq(simulacao),
        eq(dataSimulacao));
    verify(orchestrator).persistirSimulacao(any());
  }
}
