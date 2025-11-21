package org.pablofsc.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerfilClienteTest {

    @Test
    void testEnumValues() {
        // Act & Assert
        assertEquals("Conservador", PerfilCliente.CONSERVADOR.getDescricao());
        assertEquals("Foco em segurança e liquidez, movimentação baixa", PerfilCliente.CONSERVADOR.getTexto());

        assertEquals("Moderado", PerfilCliente.MODERADO.getDescricao());
        assertEquals("Equilíbrio entre segurança e rentabilidade", PerfilCliente.MODERADO.getTexto());

        assertEquals("Agressivo", PerfilCliente.AGRESSIVO.getDescricao());
        assertEquals("Busca por alta rentabilidade, disposição para maior risco", PerfilCliente.AGRESSIVO.getTexto());
    }

    @Test
    void testEnumOrdinal() {
        // Act & Assert
        assertEquals(0, PerfilCliente.CONSERVADOR.ordinal());
        assertEquals(1, PerfilCliente.MODERADO.ordinal());
        assertEquals(2, PerfilCliente.AGRESSIVO.ordinal());
    }

    @Test
    void testEnumValueOf() {
        // Act & Assert
        assertEquals(PerfilCliente.CONSERVADOR, PerfilCliente.valueOf("CONSERVADOR"));
        assertEquals(PerfilCliente.MODERADO, PerfilCliente.valueOf("MODERADO"));
        assertEquals(PerfilCliente.AGRESSIVO, PerfilCliente.valueOf("AGRESSIVO"));
    }

    @Test
    void testEnumValuesArray() {
        // Act
        PerfilCliente[] values = PerfilCliente.values();

        // Assert
        assertEquals(3, values.length);
        assertEquals(PerfilCliente.CONSERVADOR, values[0]);
        assertEquals(PerfilCliente.MODERADO, values[1]);
        assertEquals(PerfilCliente.AGRESSIVO, values[2]);
    }
}
