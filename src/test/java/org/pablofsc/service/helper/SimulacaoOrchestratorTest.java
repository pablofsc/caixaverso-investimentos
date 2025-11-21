package org.pablofsc.service.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.SimulacaoEntity;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;
import org.pablofsc.domain.exception.ClienteNaoEncontradoException;
import org.pablofsc.domain.exception.ProdutoNaoEncontradoException;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;
import org.pablofsc.repository.ClienteRepository;
import org.pablofsc.repository.SimulacaoRepository;
import org.pablofsc.service.CalculoSimulacaoService;
import org.pablofsc.service.MotorRecomendacaoService;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulacaoOrchestratorTest {

  @Mock
  private ClienteRepository clienteRepository;

  @Mock
  private SimulacaoRepository historicoRepository;

  @Mock
  private MotorRecomendacaoService motorRecomendacao;

  @Mock
  private CalculoSimulacaoService calculoService;

  private SimulacaoOrchestrator orchestrator;

  @BeforeEach
  void setUp() {
    orchestrator = new SimulacaoOrchestrator(
        clienteRepository,
        historicoRepository,
        motorRecomendacao,
        calculoService);
  }

  @Test
  void testValidarEObterCliente_Sucesso() {
    // Arrange
    Long clienteId = 1L;
    ClienteEntity cliente = ClienteEntity.builder()
        .id(clienteId)
        .nome("João Silva")
        .build();

    when(clienteRepository.findById(clienteId)).thenReturn(cliente);

    // Act
    ClienteEntity result = orchestrator.validarEObterCliente(clienteId);

    // Assert
    assertNotNull(result);
    assertEquals(clienteId, result.getId());
    assertEquals("João Silva", result.getNome());
    verify(clienteRepository).findById(clienteId);
  }

  @Test
  void testValidarEObterCliente_ClienteNaoEncontrado() {
    // Arrange
    Long clienteId = 999L;
    when(clienteRepository.findById(clienteId)).thenReturn(null);

    // Act & Assert
    ClienteNaoEncontradoException exception = assertThrows(
        ClienteNaoEncontradoException.class,
        () -> orchestrator.validarEObterCliente(clienteId));

    assertTrue(exception.getMessage().contains("999"));
    verify(clienteRepository).findById(clienteId);
  }

  @Test
  void testRecomendarEValidarProduto_Sucesso() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .build();

    String tipoProduto = "CDB";
    Integer prazoMeses = 12;

    ProdutoEntity produto = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Banco X")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    when(motorRecomendacao.recomendarProduto(cliente, tipoProduto, prazoMeses))
        .thenReturn(produto);

    // Act
    ProdutoEntity result = orchestrator.recomendarEValidarProduto(cliente, tipoProduto, prazoMeses);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("CDB Banco X", result.getNome());
    assertEquals(TipoProdutoEnum.CDB, result.getTipo());
    verify(motorRecomendacao).recomendarProduto(cliente, tipoProduto, prazoMeses);
  }

  @Test
  void testRecomendarEValidarProduto_ProdutoNaoEncontrado() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .build();

    String tipoProduto = "PRODUTO_INEXISTENTE";
    Integer prazoMeses = 12;

    when(motorRecomendacao.recomendarProduto(cliente, tipoProduto, prazoMeses))
        .thenReturn(null);

    // Act & Assert
    ProdutoNaoEncontradoException exception = assertThrows(
        ProdutoNaoEncontradoException.class,
        () -> orchestrator.recomendarEValidarProduto(cliente, tipoProduto, prazoMeses));

    assertTrue(exception.getMessage().contains("PRODUTO_INEXISTENTE"));
    verify(motorRecomendacao).recomendarProduto(cliente, tipoProduto, prazoMeses);
  }

  @Test
  void testCalcularResultado() {
    // Arrange
    Double valorInvestido = 10000.0;
    Integer prazoMeses = 12;

    ProdutoEntity produto = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Banco X")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    Double valorFinalEsperado = 11200.0;

    when(calculoService.calcularValorFinal(
        valorInvestido,
        produto.getRentabilidade(),
        prazoMeses,
        produto.getTipo()))
        .thenReturn(valorFinalEsperado);

    // Act
    Simulacao result = orchestrator.calcularResultado(valorInvestido, produto, prazoMeses);

    // Assert
    assertNotNull(result);
    assertEquals(valorFinalEsperado, result.getValorFinal());
    assertEquals(produto.getRentabilidade(), result.getRentabilidadeEfetiva());
    assertEquals(prazoMeses, result.getPrazoMeses());
    verify(calculoService).calcularValorFinal(
        valorInvestido,
        produto.getRentabilidade(),
        prazoMeses,
        produto.getTipo());
  }

  @Test
  void testConverterProdutoParaModelo() {
    // Arrange
    ProdutoEntity produtoEntity = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Banco X")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    // Act
    Produto result = orchestrator.converterProdutoParaModelo(produtoEntity);

    // Assert
    assertNotNull(result);
    assertEquals(produtoEntity.getId(), result.getId());
    assertEquals(produtoEntity.getNome(), result.getNome());
    assertEquals(produtoEntity.getTipo(), result.getTipo());
    assertEquals(produtoEntity.getRentabilidade(), result.getRentabilidade());
    assertEquals(produtoEntity.getRisco(), result.getRisco());
  }

  @Test
  void testConstruirSimulacaoEntity() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Banco X")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    Double valorInvestido = 10000.0;
    Simulacao resultado = new Simulacao(11200.0, 0.12, 12);
    ZonedDateTime dataSimulacao = ZonedDateTime.now();

    // Act
    SimulacaoEntity result = orchestrator.construirSimulacaoEntity(
        cliente, produto, valorInvestido, resultado, dataSimulacao);

    // Assert
    assertNotNull(result);
    assertEquals(cliente, result.getCliente());
    assertEquals(produto, result.getProduto());
    assertEquals(valorInvestido, result.getValorInvestido());
    assertEquals(resultado.getValorFinal(), result.getValorFinal());
    assertEquals(resultado.getPrazoMeses(), result.getPrazoMeses());
    assertEquals(dataSimulacao, result.getDataSimulacao());
  }

  @Test
  void testPersistirSimulacao() {
    // Arrange
    SimulacaoEntity simulacao = SimulacaoEntity.builder()
        .id(1L)
        .valorInvestido(10000.0)
        .valorFinal(11200.0)
        .prazoMeses(12)
        .build();

    // Act
    orchestrator.persistirSimulacao(simulacao);

    // Assert
    verify(historicoRepository).persistAndFlush(simulacao);
  }

  @Test
  void testObterDataSimulacao() {
    // Act
    ZonedDateTime result = orchestrator.obterDataSimulacao();

    // Assert
    assertNotNull(result);
    // Verifica se é UTC (não podemos testar exatamente o timestamp pois varia)
    assertEquals("Z", result.getOffset().toString());
  }
}
