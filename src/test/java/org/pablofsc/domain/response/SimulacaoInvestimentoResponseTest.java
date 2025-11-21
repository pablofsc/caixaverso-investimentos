package org.pablofsc.domain.response;

import org.junit.jupiter.api.Test;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SimulacaoInvestimentoResponseTest {

  @Test
  void testNoArgsConstructor() {
    // Act
    SimulacaoInvestimentoResponse response = new SimulacaoInvestimentoResponse();

    // Assert
    assertNotNull(response);
    assertNull(response.getProdutoValidado());
    assertNull(response.getResultadoSimulacao());
    assertNull(response.getDataSimulacao());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Produto produto = new Produto(1L, "CDB Banco X", TipoProdutoEnum.CDB, 0.12, NivelRiscoEnum.BAIXO);
    Simulacao simulacao = new Simulacao(11200.0, 0.12, 12);
    ZonedDateTime dataSimulacao = ZonedDateTime.now();

    // Act
    SimulacaoInvestimentoResponse response = new SimulacaoInvestimentoResponse(produto, simulacao, dataSimulacao);

    // Assert
    assertNotNull(response);
    assertEquals(produto, response.getProdutoValidado());
    assertEquals(simulacao, response.getResultadoSimulacao());
    assertEquals(dataSimulacao, response.getDataSimulacao());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    SimulacaoInvestimentoResponse response = new SimulacaoInvestimentoResponse();
    Produto produto = new Produto(2L, "LCI Banco Y", TipoProdutoEnum.RENDA_FIXA, 0.10, NivelRiscoEnum.MUITO_BAIXO);
    Simulacao simulacao = new Simulacao(10100.0, 0.10, 12);
    ZonedDateTime dataSimulacao = ZonedDateTime.now().minusDays(1);

    // Act
    response.setProdutoValidado(produto);
    response.setResultadoSimulacao(simulacao);
    response.setDataSimulacao(dataSimulacao);

    // Assert
    assertEquals(produto, response.getProdutoValidado());
    assertEquals(simulacao, response.getResultadoSimulacao());
    assertEquals(dataSimulacao, response.getDataSimulacao());
  }

  @Test
  void testToString() {
    // Arrange
    Produto produto = new Produto(1L, "CDB Teste", TipoProdutoEnum.CDB, 0.12, NivelRiscoEnum.BAIXO);
    Simulacao simulacao = new Simulacao(11200.0, 0.12, 12);
    ZonedDateTime dataSimulacao = ZonedDateTime.parse("2023-01-01T10:00:00Z");
    SimulacaoInvestimentoResponse response = new SimulacaoInvestimentoResponse(produto, simulacao, dataSimulacao);

    // Act
    String toString = response.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("CDB Teste"));
    assertTrue(toString.contains("11200.0"));
    assertTrue(toString.contains("2023-01-01"));
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    Produto produto1 = new Produto(1L, "Produto 1", TipoProdutoEnum.CDB, 0.12, NivelRiscoEnum.BAIXO);
    Produto produto2 = new Produto(1L, "Produto 1", TipoProdutoEnum.CDB, 0.12, NivelRiscoEnum.BAIXO);
    Simulacao simulacao1 = new Simulacao(1000.0, 0.12, 12);
    Simulacao simulacao2 = new Simulacao(1000.0, 0.12, 12);
    ZonedDateTime data1 = ZonedDateTime.parse("2023-01-01T10:00:00Z");
    ZonedDateTime data2 = ZonedDateTime.parse("2023-01-01T10:00:00Z");

    SimulacaoInvestimentoResponse response1 = new SimulacaoInvestimentoResponse(produto1, simulacao1, data1);
    SimulacaoInvestimentoResponse response2 = new SimulacaoInvestimentoResponse(produto2, simulacao2, data2);
    SimulacaoInvestimentoResponse response3 = new SimulacaoInvestimentoResponse(produto1, simulacao1, ZonedDateTime.now());

    // Act & Assert
    assertEquals(response1, response2);
    assertEquals(response1.hashCode(), response2.hashCode());
    assertNotEquals(response1, response3);
    assertNotEquals(response1.hashCode(), response3.hashCode());
  }
}
