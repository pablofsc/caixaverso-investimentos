package org.pablofsc.domain.entity;

import org.junit.jupiter.api.Test;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoEntityTest {

  @Test
  void testBuilderCreatesInstanceWithAllFields() {
    // Arrange
    Long id = 1L;
    String nome = "CDB Caixa 2026";
    TipoProdutoEnum tipo = TipoProdutoEnum.CDB;
    Double rentabilidade = 0.12;
    NivelRiscoEnum risco = NivelRiscoEnum.BAIXO;

    // Act
    ProdutoEntity produto = ProdutoEntity.builder()
        .id(id)
        .nome(nome)
        .tipo(tipo)
        .rentabilidade(rentabilidade)
        .risco(risco)
        .build();

    // Assert
    assertEquals(id, produto.getId());
    assertEquals(nome, produto.getNome());
    assertEquals(tipo, produto.getTipo());
    assertEquals(rentabilidade, produto.getRentabilidade());
    assertEquals(risco, produto.getRisco());
  }

  @Test
  void testDefaultConstructor() {
    // Act
    ProdutoEntity produto = new ProdutoEntity();

    // Assert
    assertNull(produto.getId());
    assertNull(produto.getNome());
    assertNull(produto.getTipo());
    assertNull(produto.getRentabilidade());
    assertNull(produto.getRisco());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Long id = 2L;
    String nome = "LCI Banco";
    TipoProdutoEnum tipo = TipoProdutoEnum.FUNDO;
    Double rent = 0.10;
    NivelRiscoEnum risco = NivelRiscoEnum.ALTO;

    // Act
    ProdutoEntity produto = new ProdutoEntity(id, nome, tipo, rent, risco);

    // Assert
    assertEquals(id, produto.getId());
    assertEquals(nome, produto.getNome());
    assertEquals(tipo, produto.getTipo());
    assertEquals(rent, produto.getRentabilidade());
    assertEquals(risco, produto.getRisco());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    ProdutoEntity produto = new ProdutoEntity();

    // Act
    produto.setId(3L);
    produto.setNome("Tesouro Direto");
    produto.setTipo(TipoProdutoEnum.RENDA_FIXA);
    produto.setRentabilidade(0.08);
    produto.setRisco(NivelRiscoEnum.ALTO);

    // Assert
    assertEquals(3L, produto.getId());
    assertEquals("Tesouro Direto", produto.getNome());
    assertEquals(TipoProdutoEnum.RENDA_FIXA, produto.getTipo());
    assertEquals(0.08, produto.getRentabilidade());
    assertEquals(NivelRiscoEnum.ALTO, produto.getRisco());
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    ProdutoEntity prod1 = ProdutoEntity.builder()
        .id(1L)
        .nome("Produto A")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity prod2 = ProdutoEntity.builder()
        .id(1L)
        .nome("Produto A")
        .tipo(TipoProdutoEnum.CDB)
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity prod3 = ProdutoEntity.builder()
        .id(2L)
        .nome("Produto B")
        .build();

    // Assert
    assertEquals(prod1, prod2);
    assertEquals(prod1.hashCode(), prod2.hashCode());
    assertNotEquals(prod1, prod3);
    assertNotEquals(prod1.hashCode(), prod3.hashCode());
  }

  @Test
  void testEqualsWithNull() {
    // Arrange
    ProdutoEntity produto = ProdutoEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(produto.equals(null));
  }

  @Test
  void testEqualsWithDifferentType() {
    // Arrange
    ProdutoEntity produto = ProdutoEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(produto.equals("string"));
  }

  @Test
  void testEqualsWithSameInstance() {
    // Arrange
    ProdutoEntity produto = ProdutoEntity.builder().id(1L).build();

    // Act & Assert
    assertTrue(produto.equals(produto));
  }

  @Test
  void testEqualsAndHashCodeWithNullFields() {
    // Arrange
    ProdutoEntity prod1 = ProdutoEntity.builder()
        .id(null)
        .nome(null)
        .tipo(null)
        .rentabilidade(null)
        .risco(null)
        .build();

    ProdutoEntity prod2 = ProdutoEntity.builder()
        .id(null)
        .nome(null)
        .tipo(null)
        .rentabilidade(null)
        .risco(null)
        .build();

    ProdutoEntity prod3 = ProdutoEntity.builder()
        .id(1L)
        .nome(null)
        .build();

    // Assert
    assertEquals(prod1, prod2);
    assertEquals(prod1.hashCode(), prod2.hashCode());
    assertNotEquals(prod1, prod3);
    assertNotEquals(prod1.hashCode(), prod3.hashCode());
  }

  @Test
  void testToString() {
    // Arrange
    ProdutoEntity produto = ProdutoEntity.builder()
        .id(1L)
        .nome("Test Produto")
        .build();

    // Act
    String toString = produto.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("ProdutoEntity"));
    assertTrue(toString.contains("id=1"));
    assertTrue(toString.contains("nome=Test Produto"));
  }

  @Test
  void testWithNullValues() {
    // Act
    ProdutoEntity produto = ProdutoEntity.builder()
        .id(null)
        .nome(null)
        .tipo(null)
        .rentabilidade(null)
        .risco(null)
        .build();

    // Assert
    assertNull(produto.getId());
    assertNull(produto.getNome());
    assertNull(produto.getTipo());
    assertNull(produto.getRentabilidade());
    assertNull(produto.getRisco());
  }

  @Test
  void testWithExtremeValues() {
    // Arrange
    Double highRent = Double.MAX_VALUE;
    Double lowRent = Double.MIN_VALUE;

    // Act
    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(highRent)
        .build();

    produto.setRentabilidade(lowRent);

    // Assert
    assertEquals(lowRent, produto.getRentabilidade());
  }
}
