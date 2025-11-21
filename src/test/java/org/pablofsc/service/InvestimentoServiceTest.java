package org.pablofsc.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.InvestimentoEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.enums.TipoProdutoEnum;
import org.pablofsc.domain.model.Investimento;
import org.pablofsc.repository.InvestimentoRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class InvestimentoServiceTest {

  private InvestimentoRepository investimentoRepository;
  private InvestimentoService investimentoService;

  @BeforeEach
  void setUp() {
    investimentoRepository = mock(InvestimentoRepository.class);
    investimentoService = new InvestimentoService(investimentoRepository);
  }

  @Test
  void testObterInvestimentosPorClienteComInvestimentos() {
    // Arrange
    Long clienteId = 1L;

    ClienteEntity cliente = ClienteEntity.builder().id(clienteId).build();
    ProdutoEntity produto1 = ProdutoEntity.builder()
        .id(1L)
        .nome("CDB Teste")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.08)
        .build();
    ProdutoEntity produto2 = ProdutoEntity.builder()
        .id(2L)
        .nome("Fundo Teste")
        .tipo(TipoProdutoEnum.FUNDO)
        .rentabilidade(0.10)
        .build();

    InvestimentoEntity investimento1 = InvestimentoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(produto1)
        .valor(1000.0)
        .data(LocalDate.of(2024, 3, 15))
        .build();

    InvestimentoEntity investimento2 = InvestimentoEntity.builder()
        .id(2L)
        .cliente(cliente)
        .produto(produto2)
        .valor(2000.0)
        .data(LocalDate.of(2024, 1, 10))
        .build();

    List<InvestimentoEntity> investimentos = Arrays.asList(investimento1, investimento2);

    // Mock do PanacheQuery
    @SuppressWarnings("unchecked")
    PanacheQuery<InvestimentoEntity> panacheQuery = mock(PanacheQuery.class);
    when(panacheQuery.page(0, Integer.MAX_VALUE)).thenReturn(panacheQuery);
    when(panacheQuery.list()).thenReturn(investimentos);

    when(investimentoRepository.find(eq("cliente.id"), eq(clienteId))).thenReturn(panacheQuery);

    // Act
    List<Investimento> result = investimentoService.obterInvestimentosPorCliente(clienteId);

    // Assert
    assertEquals(2, result.size());

    // Verificar ordenação por data (mais antigo primeiro)
    assertEquals(2L, result.get(0).getId());
    assertEquals(TipoProdutoEnum.FUNDO, result.get(0).getTipo());
    assertEquals(2000.0, result.get(0).getValor());
    assertEquals(0.10, result.get(0).getRentabilidade());
    assertEquals(LocalDate.of(2024, 1, 10), result.get(0).getData());

    assertEquals(1L, result.get(1).getId());
    assertEquals(TipoProdutoEnum.CDB, result.get(1).getTipo());
    assertEquals(1000.0, result.get(1).getValor());
    assertEquals(0.08, result.get(1).getRentabilidade());
    assertEquals(LocalDate.of(2024, 3, 15), result.get(1).getData());

    verify(investimentoRepository).find(eq("cliente.id"), eq(clienteId));
  }

  @Test
  void testObterInvestimentosPorClienteSemInvestimentos() {
    // Arrange
    Long clienteId = 1L;

    // Mock do PanacheQuery
    @SuppressWarnings("unchecked")
    PanacheQuery<InvestimentoEntity> panacheQuery = mock(PanacheQuery.class);
    when(panacheQuery.page(0, Integer.MAX_VALUE)).thenReturn(panacheQuery);
    when(panacheQuery.list()).thenReturn(Collections.emptyList());

    when(investimentoRepository.find(eq("cliente.id"), eq(clienteId))).thenReturn(panacheQuery);

    // Act
    List<Investimento> result = investimentoService.obterInvestimentosPorCliente(clienteId);

    // Assert
    assertTrue(result.isEmpty());
    verify(investimentoRepository).find(eq("cliente.id"), eq(clienteId));
  }

  @Test
  void testObterInvestimentosPorClienteComProdutoNull() {
    // Arrange
    Long clienteId = 1L;

    ClienteEntity cliente = ClienteEntity.builder().id(clienteId).build();

    InvestimentoEntity investimento = InvestimentoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(null) // Produto null
        .valor(1000.0)
        .data(LocalDate.of(2024, 3, 15))
        .build();

    List<InvestimentoEntity> investimentos = Arrays.asList(investimento);

    // Mock do PanacheQuery
    @SuppressWarnings("unchecked")
    PanacheQuery<InvestimentoEntity> panacheQuery = mock(PanacheQuery.class);
    when(panacheQuery.page(0, Integer.MAX_VALUE)).thenReturn(panacheQuery);
    when(panacheQuery.list()).thenReturn(investimentos);

    when(investimentoRepository.find(eq("cliente.id"), eq(clienteId))).thenReturn(panacheQuery);

    // Act
    List<Investimento> result = investimentoService.obterInvestimentosPorCliente(clienteId);

    // Assert
    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getId());
    assertNull(result.get(0).getTipo());
    assertNull(result.get(0).getRentabilidade());
    assertEquals(1000.0, result.get(0).getValor());
    assertEquals(LocalDate.of(2024, 3, 15), result.get(0).getData());

    verify(investimentoRepository).find(eq("cliente.id"), eq(clienteId));
  }
}
