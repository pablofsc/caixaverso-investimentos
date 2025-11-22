package org.pablofsc.domain.response;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

  @Test
  void testNoArgsConstructor() {
    // Act
    ErrorResponse response = new ErrorResponse();

    // Assert
    assertNotNull(response);
    assertNull(response.getMensagem());
    assertNull(response.getCodigo());
    assertNull(response.getDetalhes());
    assertNull(response.getTimestamp());
    assertNull(response.getPath());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    String mensagem = "Erro de teste";
    String codigo = "ERR_TEST_001";
    String detalhes = "Detalhes do erro de teste";
    ZonedDateTime timestamp = ZonedDateTime.now();
    String path = "/api/test";

    // Act
    ErrorResponse response = new ErrorResponse(mensagem, codigo, detalhes, timestamp, path);

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertEquals(codigo, response.getCodigo());
    assertEquals(detalhes, response.getDetalhes());
    assertEquals(timestamp, response.getTimestamp());
    assertEquals(path, response.getPath());
  }

  @Test
  void testTwoArgsConstructor() {
    // Arrange
    String mensagem = "Erro de validação";
    String codigo = "ERR_VALIDATION_422";

    // Act
    ErrorResponse response = new ErrorResponse(mensagem, codigo);

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertEquals(codigo, response.getCodigo());
    assertNull(response.getDetalhes());
    assertNotNull(response.getTimestamp());
    assertNull(response.getPath());
  }

  @Test
  void testThreeArgsConstructor() {
    // Arrange
    String mensagem = "Credenciais inválidas";
    String codigo = "ERR_AUTH_401";
    String detalhes = "Email ou senha incorretos";

    // Act
    ErrorResponse response = new ErrorResponse(mensagem, codigo, detalhes);

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertEquals(codigo, response.getCodigo());
    assertEquals(detalhes, response.getDetalhes());
    assertNotNull(response.getTimestamp());
    assertNull(response.getPath());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    ErrorResponse response = new ErrorResponse();
    String mensagem = "Recurso não encontrado";
    String codigo = "ERR_NOT_FOUND_404";
    String detalhes = "O recurso solicitado não existe";
    ZonedDateTime timestamp = ZonedDateTime.now();
    String path = "/api/resource/123";

    // Act
    response.setMensagem(mensagem);
    response.setCodigo(codigo);
    response.setDetalhes(detalhes);
    response.setTimestamp(timestamp);
    response.setPath(path);

    // Assert
    assertEquals(mensagem, response.getMensagem());
    assertEquals(codigo, response.getCodigo());
    assertEquals(detalhes, response.getDetalhes());
    assertEquals(timestamp, response.getTimestamp());
    assertEquals(path, response.getPath());
  }

  @Test
  void testToString() {
    // Arrange
    ErrorResponse response = new ErrorResponse("Erro de teste", "ERR_001", "Detalhes");

    // Act
    String toString = response.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("Erro de teste"));
    assertTrue(toString.contains("ERR_001"));
    assertTrue(toString.contains("Detalhes"));
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    ZonedDateTime now = ZonedDateTime.now();
    ErrorResponse response1 = new ErrorResponse("Erro", "ERR_001", "Detalhes", now, "/api/test");
    ErrorResponse response2 = new ErrorResponse("Erro", "ERR_001", "Detalhes", now, "/api/test");
    ErrorResponse response3 = new ErrorResponse("Outro erro", "ERR_002", "Outros detalhes", now, "/api/test2");

    // Act & Assert
    assertEquals(response1, response2);
    assertEquals(response1.hashCode(), response2.hashCode());
    assertNotEquals(response1, response3);
    assertNotEquals(response1.hashCode(), response3.hashCode());
  }

  @Test
  void testTimestampAutoGeneration() {
    // Arrange
    ZonedDateTime before = ZonedDateTime.now();

    // Act
    ErrorResponse response = new ErrorResponse("Teste", "ERR_001");
    ZonedDateTime after = ZonedDateTime.now();

    // Assert
    assertNotNull(response.getTimestamp());
    assertTrue(response.getTimestamp().isAfter(before.minusSeconds(1)));
    assertTrue(response.getTimestamp().isBefore(after.plusSeconds(1)));
  }

  @Test
  void testWithNullValues() {
    // Act
    ErrorResponse response = new ErrorResponse(null, null, null, null, null);

    // Assert
    assertNotNull(response);
    assertNull(response.getMensagem());
    assertNull(response.getCodigo());
    assertNull(response.getDetalhes());
    assertNull(response.getTimestamp());
    assertNull(response.getPath());
  }
}
