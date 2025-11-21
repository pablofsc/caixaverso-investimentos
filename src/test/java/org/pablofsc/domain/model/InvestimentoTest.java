package org.pablofsc.domain.model;

import org.junit.jupiter.api.Test;
import org.pablofsc.domain.enums.TipoProdutoEnum;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InvestimentoTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        TipoProdutoEnum tipo = TipoProdutoEnum.CDB;
        Double valor = 1000.0;
        Double rentabilidade = 0.05;
        LocalDate data = LocalDate.of(2023, 10, 1);

        // Act
        Investimento investimento = new Investimento(id, tipo, valor, rentabilidade, data);

        // Assert
        assertEquals(id, investimento.getId());
        assertEquals(tipo, investimento.getTipo());
        assertEquals(valor, investimento.getValor());
        assertEquals(rentabilidade, investimento.getRentabilidade());
        assertEquals(data, investimento.getData());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Investimento investimento = new Investimento();

        // Assert
        assertNull(investimento.getId());
        assertNull(investimento.getTipo());
        assertNull(investimento.getValor());
        assertNull(investimento.getRentabilidade());
        assertNull(investimento.getData());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Investimento investimento = new Investimento();

        // Act
        investimento.setId(2L);
        investimento.setTipo(TipoProdutoEnum.FUNDO);
        investimento.setValor(2000.0);
        investimento.setRentabilidade(0.10);
        investimento.setData(LocalDate.of(2024, 1, 1));

        // Assert
        assertEquals(2L, investimento.getId());
        assertEquals(TipoProdutoEnum.FUNDO, investimento.getTipo());
        assertEquals(2000.0, investimento.getValor());
        assertEquals(0.10, investimento.getRentabilidade());
        assertEquals(LocalDate.of(2024, 1, 1), investimento.getData());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Investimento inv1 = new Investimento(1L, TipoProdutoEnum.CDB, 1000.0, 0.05, LocalDate.of(2023, 10, 1));
        Investimento inv2 = new Investimento(1L, TipoProdutoEnum.CDB, 1000.0, 0.05, LocalDate.of(2023, 10, 1));
        Investimento inv3 = new Investimento(2L, TipoProdutoEnum.FUNDO, 2000.0, 0.10, LocalDate.of(2024, 1, 1));

        // Assert
        assertEquals(inv1, inv2);
        assertEquals(inv1.hashCode(), inv2.hashCode());
        assertNotEquals(inv1, inv3);
        assertNotEquals(inv1.hashCode(), inv3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Investimento investimento = new Investimento(1L, TipoProdutoEnum.CDB, 1000.0, 0.05, LocalDate.of(2023, 10, 1));

        // Act
        String toString = investimento.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Investimento"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("tipo=CDB"));
    }

    @Test
    void testWithNullValues() {
        // Act
        Investimento investimento = new Investimento(null, null, null, null, null);

        // Assert
        assertNull(investimento.getId());
        assertNull(investimento.getTipo());
        assertNull(investimento.getValor());
        assertNull(investimento.getRentabilidade());
        assertNull(investimento.getData());
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        Investimento investimento = new Investimento(1L, TipoProdutoEnum.CDB, 1000.0, 0.05, LocalDate.of(2023, 10, 1));

        // Act & Assert
        assertFalse(investimento.equals(null));
    }

    @Test
    void testEqualsWithDifferentType() {
        // Arrange
        Investimento investimento = new Investimento(1L, TipoProdutoEnum.CDB, 1000.0, 0.05, LocalDate.of(2023, 10, 1));

        // Act & Assert
        assertFalse(investimento.equals("string"));
    }

    @Test
    void testEqualsWithSameInstance() {
        // Arrange
        Investimento investimento = new Investimento(1L, TipoProdutoEnum.CDB, 1000.0, 0.05, LocalDate.of(2023, 10, 1));

        // Act & Assert
        assertTrue(investimento.equals(investimento));
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        // Arrange
        Investimento inv1 = new Investimento(null, null, null, null, null);
        Investimento inv2 = new Investimento(null, null, null, null, null);
        Investimento inv3 = new Investimento(1L, null, null, null, null);

        // Assert
        assertEquals(inv1, inv2);
        assertEquals(inv1.hashCode(), inv2.hashCode());
        assertNotEquals(inv1, inv3);
        assertNotEquals(inv1.hashCode(), inv3.hashCode());
    }
}
