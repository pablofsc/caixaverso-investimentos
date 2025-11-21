package org.pablofsc.domain.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulacaoInvestimentoRequestTest {

  @Test
  void testNoArgsConstructor() {
    // Act
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest();

    // Assert
    assertNotNull(request);
    assertNull(request.getClienteId());
    assertNull(request.getValor());
    assertNull(request.getPrazoMeses());
    assertNull(request.getTipoProduto());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Long clienteId = 1L;
    Double valor = 10000.0;
    Integer prazoMeses = 12;
    String tipoProduto = "CDB";

    // Act
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(clienteId, valor, prazoMeses, tipoProduto);

    // Assert
    assertNotNull(request);
    assertEquals(clienteId, request.getClienteId());
    assertEquals(valor, request.getValor());
    assertEquals(prazoMeses, request.getPrazoMeses());
    assertEquals(tipoProduto, request.getTipoProduto());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest();
    Long clienteId = 2L;
    Double valor = 5000.0;
    Integer prazoMeses = 6;
    String tipoProduto = "LCI";

    // Act
    request.setClienteId(clienteId);
    request.setValor(valor);
    request.setPrazoMeses(prazoMeses);
    request.setTipoProduto(tipoProduto);

    // Assert
    assertEquals(clienteId, request.getClienteId());
    assertEquals(valor, request.getValor());
    assertEquals(prazoMeses, request.getPrazoMeses());
    assertEquals(tipoProduto, request.getTipoProduto());
  }

  @Test
  void testToString() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(1L, 15000.0, 24, "TESOURO");

    // Act
    String toString = request.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("1"));
    assertTrue(toString.contains("15000.0"));
    assertTrue(toString.contains("24"));
    assertTrue(toString.contains("TESOURO"));
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    SimulacaoInvestimentoRequest request1 = new SimulacaoInvestimentoRequest(1L, 10000.0, 12, "CDB");
    SimulacaoInvestimentoRequest request2 = new SimulacaoInvestimentoRequest(1L, 10000.0, 12, "CDB");
    SimulacaoInvestimentoRequest request3 = new SimulacaoInvestimentoRequest(2L, 5000.0, 6, "LCI");

    // Act & Assert
    assertEquals(request1, request2);
    assertEquals(request1.hashCode(), request2.hashCode());
    assertNotEquals(request1, request3);
    assertNotEquals(request1.hashCode(), request3.hashCode());
  }

  @Test
  void testWithNullValues() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(null, null, null, null);

    // Assert
    assertNull(request.getClienteId());
    assertNull(request.getValor());
    assertNull(request.getPrazoMeses());
    assertNull(request.getTipoProduto());
  }

  @Test
  void testWithZeroValues() {
    // Arrange
    SimulacaoInvestimentoRequest request = new SimulacaoInvestimentoRequest(0L, 0.0, 0, "");

    // Assert
    assertEquals(0L, request.getClienteId());
    assertEquals(0.0, request.getValor());
    assertEquals(0, request.getPrazoMeses());
    assertEquals("", request.getTipoProduto());
  }
}
