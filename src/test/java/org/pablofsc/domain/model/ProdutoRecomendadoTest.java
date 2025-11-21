package org.pablofsc.domain.model;

import org.junit.jupiter.api.Test;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoRecomendadoTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String nome = "CDB Recomendado";
        TipoProdutoEnum tipo = TipoProdutoEnum.CDB;
        Double rentabilidade = 0.05;
        NivelRiscoEnum risco = NivelRiscoEnum.BAIXO;

        // Act
        ProdutoRecomendado produto = new ProdutoRecomendado(id, nome, tipo, rentabilidade, risco);

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
        ProdutoRecomendado produto = new ProdutoRecomendado();

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
        ProdutoRecomendado produto = new ProdutoRecomendado();

        // Act
        produto.setId(2L);
        produto.setNome("Fundo Recomendado");
        produto.setTipo(TipoProdutoEnum.FUNDO);
        produto.setRentabilidade(0.10);
        produto.setRisco(NivelRiscoEnum.ALTO);

        // Assert
        assertEquals(2L, produto.getId());
        assertEquals("Fundo Recomendado", produto.getNome());
        assertEquals(TipoProdutoEnum.FUNDO, produto.getTipo());
        assertEquals(0.10, produto.getRentabilidade());
        assertEquals(NivelRiscoEnum.ALTO, produto.getRisco());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ProdutoRecomendado p1 = new ProdutoRecomendado(1L, "CDB Recomendado", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);
        ProdutoRecomendado p2 = new ProdutoRecomendado(1L, "CDB Recomendado", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);
        ProdutoRecomendado p3 = new ProdutoRecomendado(2L, "Fundo Recomendado", TipoProdutoEnum.FUNDO, 0.10, NivelRiscoEnum.ALTO);

        // Assert
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        ProdutoRecomendado produto = new ProdutoRecomendado(1L, "CDB Recomendado", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);

        // Act
        String toString = produto.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("ProdutoRecomendado"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nome=CDB Recomendado"));
    }

    @Test
    void testWithNullValues() {
        // Act
        ProdutoRecomendado produto = new ProdutoRecomendado(null, null, null, null, null);

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
        ProdutoRecomendado produto = new ProdutoRecomendado(1L, "CDB Recomendado", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);

        // Act & Assert
        assertFalse(produto.equals(null));
    }

    @Test
    void testEqualsWithDifferentType() {
        // Arrange
        ProdutoRecomendado produto = new ProdutoRecomendado(1L, "CDB Recomendado", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);

        // Act & Assert
        assertFalse(produto.equals("string"));
    }

    @Test
    void testEqualsWithSameInstance() {
        // Arrange
        ProdutoRecomendado produto = new ProdutoRecomendado(1L, "CDB Recomendado", TipoProdutoEnum.CDB, 0.05, NivelRiscoEnum.BAIXO);

        // Act & Assert
        assertTrue(produto.equals(produto));
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        // Arrange
        ProdutoRecomendado p1 = new ProdutoRecomendado(null, null, null, null, null);
        ProdutoRecomendado p2 = new ProdutoRecomendado(null, null, null, null, null);
        ProdutoRecomendado p3 = new ProdutoRecomendado(1L, null, null, null, null);

        // Assert
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }
}
