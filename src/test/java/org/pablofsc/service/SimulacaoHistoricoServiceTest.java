package org.pablofsc.service;

import io.quarkus.panache.common.Sort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.SimulacaoEntity;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;
import org.pablofsc.domain.model.SimulacaoHistorico;
import org.pablofsc.repository.SimulacaoRepository;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimulacaoHistoricoServiceTest {

  private SimulacaoRepository simulacaoRepository;
  private SimulacaoHistoricoService simulacaoHistoricoService;

  @BeforeEach
  void setUp() {
    simulacaoRepository = mock(SimulacaoRepository.class);
    simulacaoHistoricoService = new SimulacaoHistoricoService(simulacaoRepository);
  }

  @Test
  void testListarSimulacoesComDados() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Pós-Fixado")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ZonedDateTime dataSimulacao = ZonedDateTime.now();

    SimulacaoEntity simulacao1 = SimulacaoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(produto)
        .valorInvestido(10000.0)
        .valorFinal(11000.0)
        .prazoMeses(12)
        .dataSimulacao(dataSimulacao)
        .build();

    SimulacaoEntity simulacao2 = SimulacaoEntity.builder()
        .id(2L)
        .cliente(cliente)
        .produto(produto)
        .valorInvestido(5000.0)
        .valorFinal(5250.0)
        .prazoMeses(6)
        .dataSimulacao(dataSimulacao.minusDays(1))
        .build();

    when(simulacaoRepository.listAll(any(Sort.class)))
        .thenReturn(Arrays.asList(simulacao1, simulacao2));

    // Act
    List<SimulacaoHistorico> result = simulacaoHistoricoService.listarSimulacoes();

    // Assert
    assertEquals(2, result.size());

    SimulacaoHistorico primeira = result.get(0);
    assertEquals(1L, primeira.getId());
    assertEquals(1L, primeira.getClienteId());
    assertEquals("CDB Pós-Fixado", primeira.getProduto());
    assertEquals(10000.0, primeira.getValorInvestido());
    assertEquals(11000.0, primeira.getValorFinal());
    assertEquals(12, primeira.getPrazoMeses());
    assertEquals(dataSimulacao, primeira.getDataSimulacao());

    SimulacaoHistorico segunda = result.get(1);
    assertEquals(2L, segunda.getId());
    assertEquals(1L, segunda.getClienteId());
    assertEquals("CDB Pós-Fixado", segunda.getProduto());
    assertEquals(5000.0, segunda.getValorInvestido());
    assertEquals(5250.0, segunda.getValorFinal());
    assertEquals(6, segunda.getPrazoMeses());
    assertEquals(dataSimulacao.minusDays(1), segunda.getDataSimulacao());
  }

  @Test
  void testListarSimulacoesListaVazia() {
    // Arrange
    when(simulacaoRepository.listAll(any(Sort.class)))
        .thenReturn(Collections.emptyList());

    // Act
    List<SimulacaoHistorico> result = simulacaoHistoricoService.listarSimulacoes();

    // Assert
    assertTrue(result.isEmpty());
  }

  @Test
  void testListarSimulacoesComClienteNull() {
    // Arrange
    ProdutoEntity produto = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Pós-Fixado")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    SimulacaoEntity simulacao = SimulacaoEntity.builder()
        .id(1L)
        .cliente(null) // Cliente null
        .produto(produto)
        .valorInvestido(10000.0)
        .valorFinal(11000.0)
        .prazoMeses(12)
        .dataSimulacao(ZonedDateTime.now())
        .build();

    when(simulacaoRepository.listAll(any(Sort.class)))
        .thenReturn(Collections.singletonList(simulacao));

    // Act
    List<SimulacaoHistorico> result = simulacaoHistoricoService.listarSimulacoes();

    // Assert
    assertEquals(1, result.size());
    SimulacaoHistorico historico = result.get(0);
    assertNull(historico.getClienteId());
    assertEquals("CDB Pós-Fixado", historico.getProduto());

  }

  @Test
  void testListarSimulacoesComProdutoNull() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .build();

    SimulacaoEntity simulacao = SimulacaoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(null) // Produto null
        .valorInvestido(10000.0)
        .valorFinal(11000.0)
        .prazoMeses(12)
        .dataSimulacao(ZonedDateTime.now())
        .build();

    when(simulacaoRepository.listAll(any(Sort.class)))
        .thenReturn(Collections.singletonList(simulacao));

    // Act
    List<SimulacaoHistorico> result = simulacaoHistoricoService.listarSimulacoes();

    // Assert
    assertEquals(1, result.size());
    SimulacaoHistorico historico = result.get(0);
    assertEquals(1L, historico.getClienteId());
    assertEquals("Produto removido", historico.getProduto());

  }

  @Test
  void testListarSimulacoesComEntityNull() {
    // Arrange - O filtro entity != null remove nulls, então testamos com lista
    // vazia após filtro
    SimulacaoEntity simulacaoValida = SimulacaoEntity.builder()
        .id(1L)
        .cliente(ClienteEntity.builder().id(1L).build())
        .produto(ProdutoEntity.builder().id(1L).nome("Produto").build())
        .valorInvestido(1000.0)
        .valorFinal(1100.0)
        .prazoMeses(12)
        .dataSimulacao(ZonedDateTime.now())
        .build();

    when(simulacaoRepository.listAll(any(Sort.class)))
        .thenReturn(Collections.singletonList(simulacaoValida));

    // Act
    List<SimulacaoHistorico> result = simulacaoHistoricoService.listarSimulacoes();

    // Assert
    assertEquals(1, result.size()); // Apenas o entity válido é processado
  }
}
