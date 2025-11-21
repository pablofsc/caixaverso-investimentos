package org.pablofsc.domain.model;

import org.junit.jupiter.api.Test;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String nome = "CDB Test";
        TipoProdutoEnum tipo = TipoProdutoEnum.CDB;
        Double rentabilidade = 0.05;
        NivelRiscoEnum risco = NivelRiscoEnum.BAIXO;

        // Act
        Produto produto = new Produto(id, nome, tipo, rentabilidade, risco);

        // Assert
        assertEquals(id, produto.getId());
        assertEquals(nome, produto.getNome());
        assertEquals(tipo, produto.getTipo());
        assertEquals(rentabilidade, produto.getRentabilidade());
        assertEquals(risco, produto.getRisco());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Produto produto = new Produto();

        // Assert
        assertNull(produto.getId());
        assertNull(produto.getNome());
        assertNull(produto.getTipo());
        assertNull(produto.getRentabilidade());
        assertNull(produto.getRisco());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Produto produto = new Produto();

        // Act
        produto.setId(2L);
        produto.setNome("Fundo Test");
        produto.setTipo(TipoProdutoEnum.FUNDO);
        produto.setRentabilidade(0.10);
        produto.setRisco(NivelRiscoEnum.ALTO);

        // Assert
        assertEquals(2L, produto.getId());
        assertEquals("Fundo Test", produto.getNome());
        assertEquals(TipoProdutoEnum.FUNDO, produto.getTipo());
        assertEquals(0.10, produto.getRentabilidade());
        assertEquals(NivelRiscoEnum.ALTO, produto.getRisco());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Produto p1 = new Produto(1L, "CDB Test", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);
        Produto p2 = new Produto(1L, "CDB Test", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);
        Produto p3 = new Produto(2L, "Fundo Test", TipoProdutoEnum.FUNDO, 0.10, NivelRiscoEnum.ALTO);

        // Assert
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Produto produto = new Produto(1L, "CDB Test", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);

        // Act
        String toString = produto.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Produto"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nome=CDB Test"));
    }

    @Test
    void testWithNullValues() {
        // Act
        Produto produto = new Produto(null, null, null, null, null);

        // Assert
        assertNull(produto.getId());
        assertNull(produto.getNome());
        assertNull(produto.getTipo());
        assertNull(produto.getRentabilidade());
        assertNull(produto.getRisco());
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        Produto produto = new Produto(1L, "CDB Test", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);

        // Act & Assert
        assertFalse(produto.equals(null));
    }

    @Test
    void testEqualsWithDifferentType() {
        // Arrange
        Produto produto = new Produto(1L, "CDB Test", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);

        // Act & Assert
        assertFalse(produto.equals("string"));
    }

    @Test
    void testEqualsWithSameInstance() {
        // Arrange
        Produto produto = new Produto(1L, "CDB Test", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);

        // Act & Assert
        assertTrue(produto.equals(produto));
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        // Arrange
        Produto p1 = new Produto(null, null, null, null, null);
        Produto p2 = new Produto(null, null, null, null, null);
        Produto p3 = new Produto(1L, null, null, null, null);

        // Assert
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }
}
