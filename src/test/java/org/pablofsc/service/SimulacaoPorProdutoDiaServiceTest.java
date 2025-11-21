package org.pablofsc.service;

import io.quarkus.panache.common.Sort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.SimulacaoEntity;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;
import org.pablofsc.domain.model.SimulacaoPorProdutoDia;
import org.pablofsc.repository.SimulacaoRepository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimulacaoPorProdutoDiaServiceTest {

  private SimulacaoRepository simulacaoRepository;
  private SimulacaoPorProdutoDiaService simulacaoPorProdutoDiaService;

  @BeforeEach
  void setUp() {
    simulacaoRepository = mock(SimulacaoRepository.class);
    simulacaoPorProdutoDiaService = new SimulacaoPorProdutoDiaService(simulacaoRepository);
  }

  @Test
  void testListarSimulacoesPorProdutoDiaComDados() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .build();

    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Pós-Fixado")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto2 = ProdutoEntity.builder()
        .id(2L)
        .nome("Fundo Multimercado")
        .tipo(TipoProdutoEnum.FUNDO)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    ZonedDateTime hoje = ZonedDateTime.now();
    ZonedDateTime ontem = hoje.minusDays(1);

    SimulacaoEntity simulacao1 = SimulacaoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(produto1)
        .valorInvestido(10000.0)
        .valorFinal(11000.0)
        .prazoMeses(12)
        .dataSimulacao(hoje)
        .build();

    SimulacaoEntity simulacao2 = SimulacaoEntity.builder()
        .id(2L)
        .cliente(cliente)
        .produto(produto1)
        .valorInvestido(5000.0)
        .valorFinal(5250.0)
        .prazoMeses(6)
        .dataSimulacao(hoje)
        .build();

    SimulacaoEntity simulacao3 = SimulacaoEntity.builder()
        .id(3L)
        .cliente(cliente)
        .produto(produto2)
        .valorInvestido(8000.0)
        .valorFinal(8960.0)
        .prazoMeses(12)
        .dataSimulacao(ontem)
        .build();

    when(simulacaoRepository.listAll(any(Sort.class)))
        .thenReturn(Arrays.asList(simulacao1, simulacao2, simulacao3));

    // Act
    List<SimulacaoPorProdutoDia> result = simulacaoPorProdutoDiaService.listarSimulacoesPorProdutoDia();

    // Assert
    assertEquals(2, result.size());

    // Primeiro resultado deve ser o mais recente (hoje) com CDB
    SimulacaoPorProdutoDia primeiro = result.get(0);
    assertEquals("CDB Pós-Fixado", primeiro.getProduto());
    assertEquals(hoje.toLocalDate(), primeiro.getData());
    assertEquals(2, primeiro.getQuantidadeSimulacoes());
    assertEquals(8125.0, primeiro.getMediaValorFinal()); // (11000 + 5250) / 2

    // Segundo resultado deve ser ontem com Fundo
    SimulacaoPorProdutoDia segundo = result.get(1);
    assertEquals("Fundo Multimercado", segundo.getProduto());
    assertEquals(ontem.toLocalDate(), segundo.getData());
    assertEquals(1, segundo.getQuantidadeSimulacoes());
    assertEquals(8960.0, segundo.getMediaValorFinal());
  }

  @Test
  void testListarSimulacoesPorProdutoDiaListaVazia() {
    // Arrange
    when(simulacaoRepository.listAll(any(Sort.class)))
        .thenReturn(Collections.emptyList());

    // Act
    List<SimulacaoPorProdutoDia> result = simulacaoPorProdutoDiaService.listarSimulacoesPorProdutoDia();

    // Assert
    assertTrue(result.isEmpty());
  }

  @Test
  void testListarSimulacoesPorProdutoDiaComProdutoNull() {
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
    List<SimulacaoPorProdutoDia> result = simulacaoPorProdutoDiaService.listarSimulacoesPorProdutoDia();

    // Assert
    assertEquals(1, result.size());
    SimulacaoPorProdutoDia item = result.get(0);
    assertEquals("Produto removido", item.getProduto());
    assertEquals(1, item.getQuantidadeSimulacoes());
    assertEquals(11000.0, item.getMediaValorFinal());
  }

  @Test
  void testListarSimulacoesPorProdutoDiaAgrupamentoCorreto() {
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

    LocalDate data = LocalDate.of(2024, 1, 15);

    // Múltiplas simulações no mesmo dia para o mesmo produto
    SimulacaoEntity simulacao1 = SimulacaoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(produto)
        .valorInvestido(10000.0)
        .valorFinal(11000.0)
        .prazoMeses(12)
        .dataSimulacao(data.atStartOfDay(ZonedDateTime.now().getZone()))
        .build();

    SimulacaoEntity simulacao2 = SimulacaoEntity.builder()
        .id(2L)
        .cliente(cliente)
        .produto(produto)
        .valorInvestido(20000.0)
        .valorFinal(22000.0)
        .prazoMeses(12)
        .dataSimulacao(data.atStartOfDay(ZonedDateTime.now().getZone()))
        .build();

    SimulacaoEntity simulacao3 = SimulacaoEntity.builder()
        .id(3L)
        .cliente(cliente)
        .produto(produto)
        .valorInvestido(5000.0)
        .valorFinal(5500.0)
        .prazoMeses(12)
        .dataSimulacao(data.atStartOfDay(ZonedDateTime.now().getZone()))
        .build();

    when(simulacaoRepository.listAll(any(Sort.class)))
        .thenReturn(Arrays.asList(simulacao1, simulacao2, simulacao3));

    // Act
    List<SimulacaoPorProdutoDia> result = simulacaoPorProdutoDiaService.listarSimulacoesPorProdutoDia();

    // Assert
    assertEquals(1, result.size());
    SimulacaoPorProdutoDia item = result.get(0);
    assertEquals("CDB Pós-Fixado", item.getProduto());
    assertEquals(data, item.getData());
    assertEquals(3, item.getQuantidadeSimulacoes());
    assertEquals(12833.333333333334, item.getMediaValorFinal()); // (11000 + 22000 + 5500) / 3
  }

  @Test
  void testListarSimulacoesPorProdutoDiaOrdenacao() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .build();

    ProdutoEntity produtoA = ProdutoEntity.builder()
        .id(1L)
        .nome("Produto A")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produtoB = ProdutoEntity.builder()
        .id(2L)
        .nome("Produto B")
        .tipo(TipoProdutoEnum.FUNDO)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    LocalDate ontem = LocalDate.now().minusDays(1);
    LocalDate hoje = LocalDate.now();

    SimulacaoEntity simulacao1 = SimulacaoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(produtoB)
        .valorInvestido(10000.0)
        .valorFinal(11000.0)
        .prazoMeses(12)
        .dataSimulacao(ontem.atStartOfDay(ZonedDateTime.now().getZone()))
        .build();

    SimulacaoEntity simulacao2 = SimulacaoEntity.builder()
        .id(2L)
        .cliente(cliente)
        .produto(produtoA)
        .valorInvestido(5000.0)
        .valorFinal(5250.0)
        .prazoMeses(6)
        .dataSimulacao(hoje.atStartOfDay(ZonedDateTime.now().getZone()))
        .build();

    when(simulacaoRepository.listAll(any(Sort.class)))
        .thenReturn(Arrays.asList(simulacao1, simulacao2));

    // Act
    List<SimulacaoPorProdutoDia> result = simulacaoPorProdutoDiaService.listarSimulacoesPorProdutoDia();

    // Assert
    assertEquals(2, result.size());

    // Primeiro deve ser o mais recente (hoje) com Produto A
    assertEquals("Produto A", result.get(0).getProduto());
    assertEquals(hoje, result.get(0).getData());

    // Segundo deve ser ontem com Produto B
    assertEquals("Produto B", result.get(1).getProduto());
    assertEquals(ontem, result.get(1).getData());
  }
}
