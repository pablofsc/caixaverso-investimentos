package org.pablofsc.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicoTelemetriaTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String nome = "SimulacaoService";
        Long quantidadeChamadas = 100L;
        Long mediaTempoRespostaMs = 150L;

        // Act
        ServicoTelemetria telemetria = new ServicoTelemetria(nome, quantidadeChamadas, mediaTempoRespostaMs);

        // Assert
        assertEquals(nome, telemetria.getNome());
        assertEquals(quantidadeChamadas, telemetria.getQuantidadeChamadas());
        assertEquals(mediaTempoRespostaMs, telemetria.getMediaTempoRespostaMs());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        ServicoTelemetria telemetria = new ServicoTelemetria();

        // Assert
        assertNull(telemetria.getNome());
        assertNull(telemetria.getQuantidadeChamadas());
        assertNull(telemetria.getMediaTempoRespostaMs());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        ServicoTelemetria telemetria = new ServicoTelemetria();

        // Act
        telemetria.setNome("InvestimentoService");
        telemetria.setQuantidadeChamadas(200L);
        telemetria.setMediaTempoRespostaMs(300L);

        // Assert
        assertEquals("InvestimentoService", telemetria.getNome());
        assertEquals(200L, telemetria.getQuantidadeChamadas());
        assertEquals(300L, telemetria.getMediaTempoRespostaMs());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ServicoTelemetria t1 = new ServicoTelemetria("SimulacaoService", 100L, 150L);
        ServicoTelemetria t2 = new ServicoTelemetria("SimulacaoService", 100L, 150L);
        ServicoTelemetria t3 = new ServicoTelemetria("InvestimentoService", 200L, 300L);

        // Assert
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotEquals(t1, t3);
        assertNotEquals(t1.hashCode(), t3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        ServicoTelemetria telemetria = new ServicoTelemetria("SimulacaoService", 100L, 150L);

        // Act
        String toString = telemetria.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("ServicoTelemetria"));
        assertTrue(toString.contains("nome=SimulacaoService"));
        assertTrue(toString.contains("quantidadeChamadas=100"));
        assertTrue(toString.contains("mediaTempoRespostaMs=150"));
    }

    @Test
    void testWithNullValues() {
        // Act
        ServicoTelemetria telemetria = new ServicoTelemetria(null, null, null);

        // Assert
        assertNull(telemetria.getNome());
        assertNull(telemetria.getQuantidadeChamadas());
        assertNull(telemetria.getMediaTempoRespostaMs());
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        ServicoTelemetria telemetria = new ServicoTelemetria("SimulacaoService", 100L, 150L);

        // Act & Assert
        assertFalse(telemetria.equals(null));
    }

    @Test
    void testEqualsWithDifferentType() {
        // Arrange
        ServicoTelemetria telemetria = new ServicoTelemetria("SimulacaoService", 100L, 150L);

        // Act & Assert
        assertFalse(telemetria.equals("string"));
    }

    @Test
    void testEqualsWithSameInstance() {
        // Arrange
        ServicoTelemetria telemetria = new ServicoTelemetria("SimulacaoService", 100L, 150L);

        // Act & Assert
        assertTrue(telemetria.equals(telemetria));
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        // Arrange
        ServicoTelemetria t1 = new ServicoTelemetria(null, null, null);
        ServicoTelemetria t2 = new ServicoTelemetria(null, null, null);
        ServicoTelemetria t3 = new ServicoTelemetria("SimulacaoService", null, null);

        // Assert
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotEquals(t1, t3);
        assertNotEquals(t1.hashCode(), t3.hashCode());
    }
}
