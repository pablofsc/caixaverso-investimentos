package org.pablofsc.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TelemetriaEntityTest {

  @Test
  void testBuilderCreatesInstanceWithAllFields() {
    // Arrange
    Long id = 1L;
    String endpoint = "/api/clientes";
    LocalDateTime timestamp = LocalDateTime.now();
    Long tempoRespostaMs = 150L;

    // Act
    TelemetriaEntity telemetria = TelemetriaEntity.builder()
        .id(id)
        .endpoint(endpoint)
        .timestamp(timestamp)
        .tempoRespostaMs(tempoRespostaMs)
        .build();

    // Assert
    assertEquals(id, telemetria.getId());
    assertEquals(endpoint, telemetria.getEndpoint());
    assertEquals(timestamp, telemetria.getTimestamp());
    assertEquals(tempoRespostaMs, telemetria.getTempoRespostaMs());
  }

  @Test
  void testDefaultConstructor() {
    // Act
    TelemetriaEntity telemetria = new TelemetriaEntity();

    // Assert
    assertNull(telemetria.getId());
    assertNull(telemetria.getEndpoint());
    assertNull(telemetria.getTimestamp());
    assertNull(telemetria.getTempoRespostaMs());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Long id = 2L;
    String endpoint = "/api/produtos";
    LocalDateTime timestamp = LocalDateTime.of(2023, 10, 1, 12, 0);
    Long tempo = 200L;

    // Act
    TelemetriaEntity telemetria = new TelemetriaEntity(id, endpoint, timestamp, tempo);

    // Assert
    assertEquals(id, telemetria.getId());
    assertEquals(endpoint, telemetria.getEndpoint());
    assertEquals(timestamp, telemetria.getTimestamp());
    assertEquals(tempo, telemetria.getTempoRespostaMs());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    TelemetriaEntity telemetria = new TelemetriaEntity();

    // Act
    telemetria.setId(3L);
    telemetria.setEndpoint("/api/simulacoes");
    telemetria.setTimestamp(LocalDateTime.now().minusHours(1));
    telemetria.setTempoRespostaMs(300L);

    // Assert
    assertEquals(3L, telemetria.getId());
    assertEquals("/api/simulacoes", telemetria.getEndpoint());
    assertNotNull(telemetria.getTimestamp());
    assertEquals(300L, telemetria.getTempoRespostaMs());
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    LocalDateTime time = LocalDateTime.now();

    TelemetriaEntity tel1 = TelemetriaEntity.builder()
        .id(1L)
        .endpoint("/test")
        .timestamp(time)
        .tempoRespostaMs(100L)
        .build();

    TelemetriaEntity tel2 = TelemetriaEntity.builder()
        .id(1L)
        .endpoint("/test")
        .timestamp(time)
        .tempoRespostaMs(100L)
        .build();

    TelemetriaEntity tel3 = TelemetriaEntity.builder()
        .id(2L)
        .endpoint("/other")
        .build();

    // Assert
    assertEquals(tel1, tel2);
    assertEquals(tel1.hashCode(), tel2.hashCode());
    assertNotEquals(tel1, tel3);
    assertNotEquals(tel1.hashCode(), tel3.hashCode());
  }

  @Test
  void testEqualsWithNull() {
    // Arrange
    TelemetriaEntity telemetria = TelemetriaEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(telemetria.equals(null));
  }

  @Test
  void testEqualsWithDifferentType() {
    // Arrange
    TelemetriaEntity telemetria = TelemetriaEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(telemetria.equals("string"));
  }

  @Test
  void testEqualsWithSameInstance() {
    // Arrange
    TelemetriaEntity telemetria = TelemetriaEntity.builder().id(1L).build();

    // Act & Assert
    assertTrue(telemetria.equals(telemetria));
  }

  @Test
  void testEqualsAndHashCodeWithNullFields() {
    // Arrange
    TelemetriaEntity tel1 = TelemetriaEntity.builder()
        .id(null)
        .endpoint(null)
        .timestamp(null)
        .tempoRespostaMs(null)
        .build();

    TelemetriaEntity tel2 = TelemetriaEntity.builder()
        .id(null)
        .endpoint(null)
        .timestamp(null)
        .tempoRespostaMs(null)
        .build();

    TelemetriaEntity tel3 = TelemetriaEntity.builder()
        .id(1L)
        .endpoint(null)
        .build();

    // Assert
    assertEquals(tel1, tel2);
    assertEquals(tel1.hashCode(), tel2.hashCode());
    assertNotEquals(tel1, tel3);
    assertNotEquals(tel1.hashCode(), tel3.hashCode());
  }

  @Test
  void testToString() {
    // Arrange
    TelemetriaEntity telemetria = TelemetriaEntity.builder()
        .id(1L)
        .endpoint("/test")
        .build();

    // Act
    String toString = telemetria.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("TelemetriaEntity"));
    assertTrue(toString.contains("id=1"));
    assertTrue(toString.contains("endpoint=/test"));
  }

  @Test
  void testWithNullValues() {
    // Act
    TelemetriaEntity telemetria = TelemetriaEntity.builder()
        .id(null)
        .endpoint(null)
        .timestamp(null)
        .tempoRespostaMs(null)
        .build();

    // Assert
    assertNull(telemetria.getId());
    assertNull(telemetria.getEndpoint());
    assertNull(telemetria.getTimestamp());
    assertNull(telemetria.getTempoRespostaMs());
  }

  @Test
  void testWithExtremeValues() {
    // Arrange
    Long largeTempo = Long.MAX_VALUE;
    LocalDateTime past = LocalDateTime.of(1900, 1, 1, 0, 0);
    LocalDateTime future = LocalDateTime.of(2100, 12, 31, 23, 59);

    // Act
    TelemetriaEntity telemetria = TelemetriaEntity.builder()
        .tempoRespostaMs(largeTempo)
        .timestamp(past)
        .build();

    telemetria.setTimestamp(future);

    // Assert
    assertEquals(largeTempo, telemetria.getTempoRespostaMs());
    assertEquals(future, telemetria.getTimestamp());
  }
}
