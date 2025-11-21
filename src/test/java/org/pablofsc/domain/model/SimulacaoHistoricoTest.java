package org.pablofsc.domain.model;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SimulacaoHistoricoTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        Long clienteId = 100L;
        String produto = "CDB";
        Double valorInvestido = 5000.0;
        Double valorFinal = 5250.0;
        Integer prazoMeses = 12;
        ZonedDateTime dataSimulacao = ZonedDateTime.now();

        // Act
        SimulacaoHistorico historico = new SimulacaoHistorico(id, clienteId, produto, valorInvestido, valorFinal, prazoMeses, dataSimulacao);

        // Assert
        assertEquals(id, historico.getId());
        assertEquals(clienteId, historico.getClienteId());
        assertEquals(produto, historico.getProduto());
        assertEquals(valorInvestido, historico.getValorInvestido());
        assertEquals(valorFinal, historico.getValorFinal());
        assertEquals(prazoMeses, historico.getPrazoMeses());
        assertEquals(dataSimulacao, historico.getDataSimulacao());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        SimulacaoHistorico historico = new SimulacaoHistorico();

        // Assert
        assertNull(historico.getId());
        assertNull(historico.getClienteId());
        assertNull(historico.getProduto());
        assertNull(historico.getValorInvestido());
        assertNull(historico.getValorFinal());
        assertNull(historico.getPrazoMeses());
        assertNull(historico.getDataSimulacao());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        SimulacaoHistorico historico = new SimulacaoHistorico();
        ZonedDateTime data = ZonedDateTime.now();

        // Act
        historico.setId(2L);
        historico.setClienteId(200L);
        historico.setProduto("Fundo");
        historico.setValorInvestido(10000.0);
        historico.setValorFinal(10500.0);
        historico.setPrazoMeses(24);
        historico.setDataSimulacao(data);

        // Assert
        assertEquals(2L, historico.getId());
        assertEquals(200L, historico.getClienteId());
        assertEquals("Fundo", historico.getProduto());
        assertEquals(10000.0, historico.getValorInvestido());
        assertEquals(10500.0, historico.getValorFinal());
        assertEquals(24, historico.getPrazoMeses());
        assertEquals(data, historico.getDataSimulacao());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ZonedDateTime data = ZonedDateTime.now();
        SimulacaoHistorico h1 = new SimulacaoHistorico(1L, 100L, "CDB", 5000.0, 5250.0, 12, data);
        SimulacaoHistorico h2 = new SimulacaoHistorico(1L, 100L, "CDB", 5000.0, 5250.0, 12, data);
        SimulacaoHistorico h3 = new SimulacaoHistorico(2L, 200L, "Fundo", 10000.0, 10500.0, 24, data.plusDays(1));

        // Assert
        assertEquals(h1, h2);
        assertEquals(h1.hashCode(), h2.hashCode());
        assertNotEquals(h1, h3);
        assertNotEquals(h1.hashCode(), h3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        ZonedDateTime data = ZonedDateTime.now();
        SimulacaoHistorico historico = new SimulacaoHistorico(1L, 100L, "CDB", 5000.0, 5250.0, 12, data);

        // Act
        String toString = historico.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("SimulacaoHistorico"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("clienteId=100"));
        assertTrue(toString.contains("produto=CDB"));
    }

    @Test
    void testWithNullValues() {
        // Act
        SimulacaoHistorico historico = new SimulacaoHistorico(null, null, null, null, null, null, null);

        // Assert
        assertNull(historico.getId());
        assertNull(historico.getClienteId());
        assertNull(historico.getProduto());
        assertNull(historico.getValorInvestido());
        assertNull(historico.getValorFinal());
        assertNull(historico.getPrazoMeses());
        assertNull(historico.getDataSimulacao());
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        ZonedDateTime data = ZonedDateTime.now();
        SimulacaoHistorico historico = new SimulacaoHistorico(1L, 100L, "CDB", 5000.0, 5250.0, 12, data);

        // Act & Assert
        assertFalse(historico.equals(null));
    }

    @Test
    void testEqualsWithDifferentType() {
        // Arrange
        ZonedDateTime data = ZonedDateTime.now();
        SimulacaoHistorico historico = new SimulacaoHistorico(1L, 100L, "CDB", 5000.0, 5250.0, 12, data);

        // Act & Assert
        assertFalse(historico.equals("string"));
    }

    @Test
    void testEqualsWithSameInstance() {
        // Arrange
        ZonedDateTime data = ZonedDateTime.now();
        SimulacaoHistorico historico = new SimulacaoHistorico(1L, 100L, "CDB", 5000.0, 5250.0, 12, data);

        // Act & Assert
        assertTrue(historico.equals(historico));
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        // Arrange
        SimulacaoHistorico h1 = new SimulacaoHistorico(null, null, null, null, null, null, null);
        SimulacaoHistorico h2 = new SimulacaoHistorico(null, null, null, null, null, null, null);
        SimulacaoHistorico h3 = new SimulacaoHistorico(1L, null, null, null, null, null, null);

        // Assert
        assertEquals(h1, h2);
        assertEquals(h1.hashCode(), h2.hashCode());
        assertNotEquals(h1, h3);
        assertNotEquals(h1.hashCode(), h3.hashCode());
    }
}
