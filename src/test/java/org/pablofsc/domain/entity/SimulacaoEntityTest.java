package org.pablofsc.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SimulacaoEntityTest {

  @Test
  void testBuilderCreatesInstanceWithAllFields() {
    // Arrange
    Long id = 1L;
    ClienteEntity cliente = ClienteEntity.builder().id(1L).nome("Cliente").build();
    ProdutoEntity produto = ProdutoEntity.builder().id(1L).nome("Produto").build();
    Double valorInvestido = 5000.0;
    Double valorFinal = 5500.0;
    Integer prazoMeses = 12;
    ZonedDateTime dataSimulacao = ZonedDateTime.now();

    // Act
    SimulacaoEntity simulacao = SimulacaoEntity.builder()
        .id(id)
        .cliente(cliente)
        .produto(produto)
        .valorInvestido(valorInvestido)
        .valorFinal(valorFinal)
        .prazoMeses(prazoMeses)
        .dataSimulacao(dataSimulacao)
        .build();

    // Assert
    assertEquals(id, simulacao.getId());
    assertEquals(cliente, simulacao.getCliente());
    assertEquals(produto, simulacao.getProduto());
    assertEquals(valorInvestido, simulacao.getValorInvestido());
    assertEquals(valorFinal, simulacao.getValorFinal());
    assertEquals(prazoMeses, simulacao.getPrazoMeses());
    assertEquals(dataSimulacao, simulacao.getDataSimulacao());
  }

  @Test
  void testDefaultConstructor() {
    // Act
    SimulacaoEntity simulacao = new SimulacaoEntity();

    // Assert
    assertNull(simulacao.getId());
    assertNull(simulacao.getCliente());
    assertNull(simulacao.getProduto());
    assertNull(simulacao.getValorInvestido());
    assertNull(simulacao.getValorFinal());
    assertNull(simulacao.getPrazoMeses());
    assertNull(simulacao.getDataSimulacao());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Long id = 2L;
    ClienteEntity cliente = ClienteEntity.builder().id(2L).build();
    ProdutoEntity produto = ProdutoEntity.builder().id(2L).build();
    Double valInv = 10000.0;
    Double valFin = 11000.0;
    Integer prazo = 24;
    ZonedDateTime data = ZonedDateTime.now().minusDays(1);

    // Act
    SimulacaoEntity simulacao = new SimulacaoEntity(id, cliente, produto, valInv, valFin, prazo, data);

    // Assert
    assertEquals(id, simulacao.getId());
    assertEquals(cliente, simulacao.getCliente());
    assertEquals(produto, simulacao.getProduto());
    assertEquals(valInv, simulacao.getValorInvestido());
    assertEquals(valFin, simulacao.getValorFinal());
    assertEquals(prazo, simulacao.getPrazoMeses());
    assertEquals(data, simulacao.getDataSimulacao());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    SimulacaoEntity simulacao = new SimulacaoEntity();

    // Act
    simulacao.setId(3L);
    simulacao.setCliente(ClienteEntity.builder().id(3L).build());
    simulacao.setProduto(ProdutoEntity.builder().id(3L).build());
    simulacao.setValorInvestido(7500.0);
    simulacao.setValorFinal(8000.0);
    simulacao.setPrazoMeses(18);
    simulacao.setDataSimulacao(ZonedDateTime.of(2023, 5, 15, 10, 30, 0, 0, ZonedDateTime.now().getZone()));

    // Assert
    assertEquals(3L, simulacao.getId());
    assertNotNull(simulacao.getCliente());
    assertNotNull(simulacao.getProduto());
    assertEquals(7500.0, simulacao.getValorInvestido());
    assertEquals(8000.0, simulacao.getValorFinal());
    assertEquals(18, simulacao.getPrazoMeses());
    assertNotNull(simulacao.getDataSimulacao());
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder().id(1L).build();
    ProdutoEntity produto = ProdutoEntity.builder().id(1L).build();
    ZonedDateTime data = ZonedDateTime.now();

    SimulacaoEntity sim1 = SimulacaoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(produto)
        .valorInvestido(1000.0)
        .valorFinal(1100.0)
        .prazoMeses(12)
        .dataSimulacao(data)
        .build();

    SimulacaoEntity sim2 = SimulacaoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(produto)
        .valorInvestido(1000.0)
        .valorFinal(1100.0)
        .prazoMeses(12)
        .dataSimulacao(data)
        .build();

    SimulacaoEntity sim3 = SimulacaoEntity.builder()
        .id(2L)
        .cliente(ClienteEntity.builder().id(2L).build())
        .build();

    // Assert
    assertEquals(sim1, sim2);
    assertEquals(sim1.hashCode(), sim2.hashCode());
    assertNotEquals(sim1, sim3);
    assertNotEquals(sim1.hashCode(), sim3.hashCode());
  }

  @Test
  void testEqualsWithNull() {
    // Arrange
    SimulacaoEntity simulacao = SimulacaoEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(simulacao.equals(null));
  }

  @Test
  void testEqualsWithDifferentType() {
    // Arrange
    SimulacaoEntity simulacao = SimulacaoEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(simulacao.equals("string"));
  }

  @Test
  void testEqualsWithSameInstance() {
    // Arrange
    SimulacaoEntity simulacao = SimulacaoEntity.builder().id(1L).build();

    // Act & Assert
    assertTrue(simulacao.equals(simulacao));
  }

  @Test
  void testEqualsAndHashCodeWithNullFields() {
    // Arrange
    SimulacaoEntity sim1 = SimulacaoEntity.builder()
        .id(null)
        .cliente(null)
        .produto(null)
        .valorInvestido(null)
        .valorFinal(null)
        .prazoMeses(null)
        .dataSimulacao(null)
        .build();

    SimulacaoEntity sim2 = SimulacaoEntity.builder()
        .id(null)
        .cliente(null)
        .produto(null)
        .valorInvestido(null)
        .valorFinal(null)
        .prazoMeses(null)
        .dataSimulacao(null)
        .build();

    SimulacaoEntity sim3 = SimulacaoEntity.builder()
        .id(1L)
        .cliente(null)
        .build();

    // Assert
    assertEquals(sim1, sim2);
    assertEquals(sim1.hashCode(), sim2.hashCode());
    assertNotEquals(sim1, sim3);
    assertNotEquals(sim1.hashCode(), sim3.hashCode());
  }

  @Test
  void testToString() {
    // Arrange
    SimulacaoEntity simulacao = SimulacaoEntity.builder()
        .id(1L)
        .valorInvestido(1000.0)
        .build();

    // Act
    String toString = simulacao.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("SimulacaoEntity"));
    assertTrue(toString.contains("id=1"));
    assertTrue(toString.contains("valorInvestido=1000.0"));
  }

  @Test
  void testWithNullValues() {
    // Act
    SimulacaoEntity simulacao = SimulacaoEntity.builder()
        .id(null)
        .cliente(null)
        .produto(null)
        .valorInvestido(null)
        .valorFinal(null)
        .prazoMeses(null)
        .dataSimulacao(null)
        .build();

    // Assert
    assertNull(simulacao.getId());
    assertNull(simulacao.getCliente());
    assertNull(simulacao.getProduto());
    assertNull(simulacao.getValorInvestido());
    assertNull(simulacao.getValorFinal());
    assertNull(simulacao.getPrazoMeses());
    assertNull(simulacao.getDataSimulacao());
  }

  @Test
  void testWithExtremeValues() {
    // Arrange
    Double largeValor = Double.MAX_VALUE;
    Integer largePrazo = Integer.MAX_VALUE;
    ZonedDateTime past = ZonedDateTime.of(1900, 1, 1, 0, 0, 0, 0, ZonedDateTime.now().getZone());
    ZonedDateTime future = ZonedDateTime.of(2100, 12, 31, 23, 59, 59, 0, ZonedDateTime.now().getZone());

    // Act
    SimulacaoEntity simulacao = SimulacaoEntity.builder()
        .valorInvestido(largeValor)
        .valorFinal(largeValor)
        .prazoMeses(largePrazo)
        .dataSimulacao(past)
        .build();

    simulacao.setDataSimulacao(future);

    // Assert
    assertEquals(largeValor, simulacao.getValorInvestido());
    assertEquals(largeValor, simulacao.getValorFinal());
    assertEquals(largePrazo, simulacao.getPrazoMeses());
    assertEquals(future, simulacao.getDataSimulacao());
  }
}
