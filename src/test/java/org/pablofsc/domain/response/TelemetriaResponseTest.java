package org.pablofsc.domain.response;

import org.junit.jupiter.api.Test;
import org.pablofsc.domain.model.Periodo;
import org.pablofsc.domain.model.ServicoTelemetria;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TelemetriaResponseTest {

  @Test
  void testNoArgsConstructor() {
    // Act
    TelemetriaResponse response = new TelemetriaResponse();

    // Assert
    assertNotNull(response);
    assertNull(response.getServicos());
    assertNull(response.getPeriodo());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    List<ServicoTelemetria> servicos = Arrays.asList(
        new ServicoTelemetria("CDB", 10L, 100L),
        new ServicoTelemetria("LCI", 5L, 50L)
    );
    Periodo periodo = new Periodo(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

    // Act
    TelemetriaResponse response = new TelemetriaResponse(servicos, periodo);

    // Assert
    assertNotNull(response);
    assertEquals(servicos, response.getServicos());
    assertEquals(periodo, response.getPeriodo());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    TelemetriaResponse response = new TelemetriaResponse();
    List<ServicoTelemetria> servicos = Collections.singletonList(
        new ServicoTelemetria("TESOURO", 3L, 30L)
    );
    Periodo periodo = new Periodo(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 6, 30));

    // Act
    response.setServicos(servicos);
    response.setPeriodo(periodo);

    // Assert
    assertEquals(servicos, response.getServicos());
    assertEquals(periodo, response.getPeriodo());
  }

  @Test
  void testToString() {
    // Arrange
    List<ServicoTelemetria> servicos = Arrays.asList(
        new ServicoTelemetria("CDB", 1L, 10L)
    );
    Periodo periodo = new Periodo(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31));
    TelemetriaResponse response = new TelemetriaResponse(servicos, periodo);

    // Act
    String toString = response.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("CDB"));
    assertTrue(toString.contains("2023-01-01"));
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    List<ServicoTelemetria> servicos1 = Arrays.asList(
        new ServicoTelemetria("CDB", 10L, 100L)
    );
    List<ServicoTelemetria> servicos2 = Arrays.asList(
        new ServicoTelemetria("CDB", 10L, 100L)
    );
    Periodo periodo1 = new Periodo(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
    Periodo periodo2 = new Periodo(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

    TelemetriaResponse response1 = new TelemetriaResponse(servicos1, periodo1);
    TelemetriaResponse response2 = new TelemetriaResponse(servicos2, periodo2);
    TelemetriaResponse response3 = new TelemetriaResponse(Collections.emptyList(), periodo1);

    // Act & Assert
    assertEquals(response1, response2);
    assertEquals(response1.hashCode(), response2.hashCode());
    assertNotEquals(response1, response3);
    assertNotEquals(response1.hashCode(), response3.hashCode());
  }

  @Test
  void testWithEmptyServicosList() {
    // Arrange
    List<ServicoTelemetria> servicos = Collections.emptyList();
    Periodo periodo = new Periodo(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31));

    // Act
    TelemetriaResponse response = new TelemetriaResponse(servicos, periodo);

    // Assert
    assertNotNull(response);
    assertTrue(response.getServicos().isEmpty());
    assertEquals(periodo, response.getPeriodo());
  }
}
