package org.pablofsc.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PeriodoTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        LocalDate inicio = LocalDate.of(2023, 1, 1);
        LocalDate fim = LocalDate.of(2023, 12, 31);

        // Act
        Periodo periodo = new Periodo(inicio, fim);

        // Assert
        assertEquals(inicio, periodo.getInicio());
        assertEquals(fim, periodo.getFim());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Periodo periodo = new Periodo();

        // Assert
        assertNull(periodo.getInicio());
        assertNull(periodo.getFim());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Periodo periodo = new Periodo();

        // Act
        periodo.setInicio(LocalDate.of(2024, 1, 1));
        periodo.setFim(LocalDate.of(2024, 12, 31));

        // Assert
        assertEquals(LocalDate.of(2024, 1, 1), periodo.getInicio());
        assertEquals(LocalDate.of(2024, 12, 31), periodo.getFim());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        LocalDate inicio = LocalDate.of(2023, 1, 1);
        LocalDate fim = LocalDate.of(2023, 12, 31);
        Periodo p1 = new Periodo(inicio, fim);
        Periodo p2 = new Periodo(inicio, fim);
        Periodo p3 = new Periodo(LocalDate.of(2024, 1, 1), fim);

        // Assert
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Periodo periodo = new Periodo(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

        // Act
        String toString = periodo.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Periodo"));
    }

    @Test
    void testWithNullValues() {
        // Act
        Periodo periodo = new Periodo(null, null);

        // Assert
        assertNull(periodo.getInicio());
        assertNull(periodo.getFim());
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        Periodo periodo = new Periodo(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

        // Act & Assert
        assertFalse(periodo.equals(null));
    }

    @Test
    void testEqualsWithDifferentType() {
        // Arrange
        Periodo periodo = new Periodo(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

        // Act & Assert
        assertFalse(periodo.equals("string"));
    }

    @Test
    void testEqualsWithSameInstance() {
        // Arrange
        Periodo periodo = new Periodo(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

        // Act & Assert
        assertTrue(periodo.equals(periodo));
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        // Arrange
        Periodo p1 = new Periodo(null, null);
        Periodo p2 = new Periodo(null, null);
        Periodo p3 = new Periodo(LocalDate.of(2023, 1, 1), null);

        // Assert
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }
}
