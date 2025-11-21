package org.pablofsc.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulacaoTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Double valorFinal = 1000.0;
        Double rentabilidadeEfetiva = 0.05;
        Integer prazoMeses = 12;

        // Act
        Simulacao simulacao = new Simulacao(valorFinal, rentabilidadeEfetiva, prazoMeses);

        // Assert
        assertEquals(valorFinal, simulacao.getValorFinal());
        assertEquals(rentabilidadeEfetiva, simulacao.getRentabilidadeEfetiva());
        assertEquals(prazoMeses, simulacao.getPrazoMeses());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Simulacao simulacao = new Simulacao();

        // Assert
        assertNull(simulacao.getValorFinal());
        assertNull(simulacao.getRentabilidadeEfetiva());
        assertNull(simulacao.getPrazoMeses());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Simulacao simulacao = new Simulacao();

        // Act
        simulacao.setValorFinal(2000.0);
        simulacao.setRentabilidadeEfetiva(0.10);
        simulacao.setPrazoMeses(24);

        // Assert
        assertEquals(2000.0, simulacao.getValorFinal());
        assertEquals(0.10, simulacao.getRentabilidadeEfetiva());
        assertEquals(24, simulacao.getPrazoMeses());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Simulacao s1 = new Simulacao(1000.0, 0.05, 12);
        Simulacao s2 = new Simulacao(1000.0, 0.05, 12);
        Simulacao s3 = new Simulacao(2000.0, 0.10, 24);

        // Assert
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
        assertNotEquals(s1.hashCode(), s3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Simulacao simulacao = new Simulacao(1000.0, 0.05, 12);

        // Act
        String toString = simulacao.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Simulacao"));
        assertTrue(toString.contains("valorFinal=1000.0"));
        assertTrue(toString.contains("rentabilidadeEfetiva=0.05"));
        assertTrue(toString.contains("prazoMeses=12"));
    }

    @Test
    void testWithNullValues() {
        // Act
        Simulacao simulacao = new Simulacao(null, null, null);

        // Assert
        assertNull(simulacao.getValorFinal());
        assertNull(simulacao.getRentabilidadeEfetiva());
        assertNull(simulacao.getPrazoMeses());
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        Simulacao simulacao = new Simulacao(1000.0, 0.05, 12);

        // Act & Assert
        assertFalse(simulacao.equals(null));
    }

    @Test
    void testEqualsWithDifferentType() {
        // Arrange
        Simulacao simulacao = new Simulacao(1000.0, 0.05, 12);

        // Act & Assert
        assertFalse(simulacao.equals("string"));
    }

    @Test
    void testEqualsWithSameInstance() {
        // Arrange
        Simulacao simulacao = new Simulacao(1000.0, 0.05, 12);

        // Act & Assert
        assertTrue(simulacao.equals(simulacao));
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        // Arrange
        Simulacao s1 = new Simulacao(null, null, null);
        Simulacao s2 = new Simulacao(null, null, null);
        Simulacao s3 = new Simulacao(1000.0, null, null);

        // Assert
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
        assertNotEquals(s1.hashCode(), s3.hashCode());
    }
}
