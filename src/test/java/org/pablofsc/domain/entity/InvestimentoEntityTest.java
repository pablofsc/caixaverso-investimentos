package org.pablofsc.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InvestimentoEntityTest {

  @Test
  void testBuilderCreatesInstanceWithAllFields() {
    // Arrange
    Long id = 1L;
    ClienteEntity cliente = ClienteEntity.builder().id(1L).nome("Cliente Test").build();
    ProdutoEntity produto = ProdutoEntity.builder().id(1L).nome("Produto Test").build();
    Double valor = 1000.0;
    LocalDate data = LocalDate.of(2023, 10, 1);

    // Act
    InvestimentoEntity investimento = InvestimentoEntity.builder()
        .id(id)
        .cliente(cliente)
        .produto(produto)
        .valor(valor)
        .data(data)
        .build();

    // Assert
    assertEquals(id, investimento.getId());
    assertEquals(cliente, investimento.getCliente());
    assertEquals(produto, investimento.getProduto());
    assertEquals(valor, investimento.getValor());
    assertEquals(data, investimento.getData());
  }

  @Test
  void testDefaultConstructor() {
    // Act
    InvestimentoEntity investimento = new InvestimentoEntity();

    // Assert
    assertNull(investimento.getId());
    assertNull(investimento.getCliente());
    assertNull(investimento.getProduto());
    assertNull(investimento.getValor());
    assertNull(investimento.getData());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Long id = 2L;
    ClienteEntity cliente = ClienteEntity.builder().id(2L).build();
    ProdutoEntity produto = ProdutoEntity.builder().id(2L).build();
    Double valor = 2000.0;
    LocalDate data = LocalDate.now();

    // Act
    InvestimentoEntity investimento = new InvestimentoEntity(id, cliente, produto, valor, data);

    // Assert
    assertEquals(id, investimento.getId());
    assertEquals(cliente, investimento.getCliente());
    assertEquals(produto, investimento.getProduto());
    assertEquals(valor, investimento.getValor());
    assertEquals(data, investimento.getData());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    InvestimentoEntity investimento = new InvestimentoEntity();

    // Act
    investimento.setId(3L);
    investimento.setCliente(ClienteEntity.builder().id(3L).build());
    investimento.setProduto(ProdutoEntity.builder().id(3L).build());
    investimento.setValor(3000.0);
    investimento.setData(LocalDate.of(2024, 1, 1));

    // Assert
    assertEquals(3L, investimento.getId());
    assertNotNull(investimento.getCliente());
    assertNotNull(investimento.getProduto());
    assertEquals(3000.0, investimento.getValor());
    assertEquals(LocalDate.of(2024, 1, 1), investimento.getData());
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder().id(1L).nome("Cliente").build();
    ProdutoEntity produto = ProdutoEntity.builder().id(1L).nome("Produto").build();
    LocalDate data = LocalDate.of(2023, 1, 1);

    InvestimentoEntity inv1 = InvestimentoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(produto)
        .valor(1000.0)
        .data(data)
        .build();

    InvestimentoEntity inv2 = InvestimentoEntity.builder()
        .id(1L)
        .cliente(cliente)
        .produto(produto)
        .valor(1000.0)
        .data(data)
        .build();

    InvestimentoEntity inv3 = InvestimentoEntity.builder()
        .id(2L)
        .cliente(ClienteEntity.builder().id(2L).build())
        .build();

    // Assert
    assertEquals(inv1, inv2);
    assertEquals(inv1.hashCode(), inv2.hashCode());
    assertNotEquals(inv1, inv3);
    assertNotEquals(inv1.hashCode(), inv3.hashCode());
  }

  @Test
  void testEqualsWithNull() {
    // Arrange
    InvestimentoEntity investimento = InvestimentoEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(investimento.equals(null));
  }

  @Test
  void testEqualsWithDifferentType() {
    // Arrange
    InvestimentoEntity investimento = InvestimentoEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(investimento.equals("string"));
  }

  @Test
  void testEqualsWithSameInstance() {
    // Arrange
    InvestimentoEntity investimento = InvestimentoEntity.builder().id(1L).build();

    // Act & Assert
    assertTrue(investimento.equals(investimento));
  }

  @Test
  void testEqualsAndHashCodeWithNullFields() {
    // Arrange
    InvestimentoEntity inv1 = InvestimentoEntity.builder()
        .id(null)
        .cliente(null)
        .produto(null)
        .valor(null)
        .data(null)
        .build();

    InvestimentoEntity inv2 = InvestimentoEntity.builder()
        .id(null)
        .cliente(null)
        .produto(null)
        .valor(null)
        .data(null)
        .build();

    InvestimentoEntity inv3 = InvestimentoEntity.builder()
        .id(1L)
        .cliente(null)
        .build();

    // Assert
    assertEquals(inv1, inv2);
    assertEquals(inv1.hashCode(), inv2.hashCode());
    assertNotEquals(inv1, inv3);
    assertNotEquals(inv1.hashCode(), inv3.hashCode());
  }

  @Test
  void testToString() {
    // Arrange
    InvestimentoEntity investimento = InvestimentoEntity.builder()
        .id(1L)
        .valor(1000.0)
        .build();

    // Act
    String toString = investimento.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("InvestimentoEntity"));
    assertTrue(toString.contains("id=1"));
    assertTrue(toString.contains("valor=1000.0"));
  }

  @Test
  void testWithNullValues() {
    // Act
    InvestimentoEntity investimento = InvestimentoEntity.builder()
        .id(null)
        .cliente(null)
        .produto(null)
        .valor(null)
        .data(null)
        .build();

    // Assert
    assertNull(investimento.getId());
    assertNull(investimento.getCliente());
    assertNull(investimento.getProduto());
    assertNull(investimento.getValor());
    assertNull(investimento.getData());
  }

  @Test
  void testWithExtremeValues() {
    // Arrange
    Double largeValor = Double.MAX_VALUE;
    LocalDate pastDate = LocalDate.of(1900, 1, 1);
    LocalDate futureDate = LocalDate.of(2100, 12, 31);

    // Act
    InvestimentoEntity investimento = InvestimentoEntity.builder()
        .valor(largeValor)
        .data(pastDate)
        .build();

    investimento.setData(futureDate);

    // Assert
    assertEquals(largeValor, investimento.getValor());
    assertEquals(futureDate, investimento.getData());
  }
}
