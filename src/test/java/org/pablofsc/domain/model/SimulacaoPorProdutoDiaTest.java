package org.pablofsc.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SimulacaoPorProdutoDiaTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String produto = "CDB";
        LocalDate data = LocalDate.now();
        Integer quantidadeSimulacoes = 10;
        Double mediaValorFinal = 5250.0;

        // Act
        SimulacaoPorProdutoDia simulacao = new SimulacaoPorProdutoDia(produto, data, quantidadeSimulacoes, mediaValorFinal);

        // Assert
        assertEquals(produto, simulacao.getProduto());
        assertEquals(data, simulacao.getData());
        assertEquals(quantidadeSimulacoes, simulacao.getQuantidadeSimulacoes());
        assertEquals(mediaValorFinal, simulacao.getMediaValorFinal());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        SimulacaoPorProdutoDia simulacao = new SimulacaoPorProdutoDia();

        // Assert
        assertNull(simulacao.getProduto());
        assertNull(simulacao.getData());
        assertNull(simulacao.getQuantidadeSimulacoes());
        assertNull(simulacao.getMediaValorFinal());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        SimulacaoPorProdutoDia simulacao = new SimulacaoPorProdutoDia();
        LocalDate data = LocalDate.now();

        // Act
        simulacao.setProduto("Fundo");
        simulacao.setData(data);
        simulacao.setQuantidadeSimulacoes(20);
        simulacao.setMediaValorFinal(10500.0);

        // Assert
        assertEquals("Fundo", simulacao.getProduto());
        assertEquals(data, simulacao.getData());
        assertEquals(20, simulacao.getQuantidadeSimulacoes());
        assertEquals(10500.0, simulacao.getMediaValorFinal());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        LocalDate data = LocalDate.now();
        SimulacaoPorProdutoDia s1 = new SimulacaoPorProdutoDia("CDB", data, 10, 5250.0);
        SimulacaoPorProdutoDia s2 = new SimulacaoPorProdutoDia("CDB", data, 10, 5250.0);
        SimulacaoPorProdutoDia s3 = new SimulacaoPorProdutoDia("Fundo", data.plusDays(1), 20, 10500.0);

        // Assert
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
        assertNotEquals(s1.hashCode(), s3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        LocalDate data = LocalDate.now();
        SimulacaoPorProdutoDia simulacao = new SimulacaoPorProdutoDia("CDB", data, 10, 5250.0);

        // Act
        String toString = simulacao.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("SimulacaoPorProdutoDia"));
        assertTrue(toString.contains("produto=CDB"));
        assertTrue(toString.contains("quantidadeSimulacoes=10"));
        assertTrue(toString.contains("mediaValorFinal=5250.0"));
    }

    @Test
    void testWithNullValues() {
        // Act
        SimulacaoPorProdutoDia simulacao = new SimulacaoPorProdutoDia(null, null, null, null);

        // Assert
        assertNull(simulacao.getProduto());
        assertNull(simulacao.getData());
        assertNull(simulacao.getQuantidadeSimulacoes());
        assertNull(simulacao.getMediaValorFinal());
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        LocalDate data = LocalDate.now();
        SimulacaoPorProdutoDia simulacao = new SimulacaoPorProdutoDia("CDB", data, 10, 5250.0);

        // Act & Assert
        assertFalse(simulacao.equals(null));
    }

    @Test
    void testEqualsWithDifferentType() {
        // Arrange
        LocalDate data = LocalDate.now();
        SimulacaoPorProdutoDia simulacao = new SimulacaoPorProdutoDia("CDB", data, 10, 5250.0);

        // Act & Assert
        assertFalse(simulacao.equals("string"));
    }

    @Test
    void testEqualsWithSameInstance() {
        // Arrange
        LocalDate data = LocalDate.now();
        SimulacaoPorProdutoDia simulacao = new SimulacaoPorProdutoDia("CDB", data, 10, 5250.0);

        // Act & Assert
        assertTrue(simulacao.equals(simulacao));
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        // Arrange
        SimulacaoPorProdutoDia s1 = new SimulacaoPorProdutoDia(null, null, null, null);
        SimulacaoPorProdutoDia s2 = new SimulacaoPorProdutoDia(null, null, null, null);
        SimulacaoPorProdutoDia s3 = new SimulacaoPorProdutoDia("CDB", null, null, null);

        // Assert
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
        assertNotEquals(s1.hashCode(), s3.hashCode());
    }
}
