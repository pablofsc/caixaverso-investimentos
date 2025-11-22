package org.pablofsc.domain.response;

import org.junit.jupiter.api.Test;
import org.pablofsc.domain.response.ValidationErrorResponse.FieldError;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationErrorResponseTest {

  @Test
  void testNoArgsConstructor() {
    // Act
    ValidationErrorResponse response = new ValidationErrorResponse();

    // Assert
    assertNotNull(response);
    assertNull(response.getMensagem());
    assertNull(response.getCodigo());
    assertNull(response.getErros());
    assertNull(response.getTimestamp());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    String mensagem = "Erro de validação";
    String codigo = "ERR_VALIDATION_422";
    List<FieldError> erros = List.of(
        new FieldError("email", "Email inválido", "invalid@"),
        new FieldError("senha", "Senha muito curta", "123")
    );
    ZonedDateTime timestamp = ZonedDateTime.now();

    // Act
    ValidationErrorResponse response = new ValidationErrorResponse(mensagem, codigo, erros, timestamp);

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertEquals(codigo, response.getCodigo());
    assertEquals(erros, response.getErros());
    assertEquals(timestamp, response.getTimestamp());
  }

  @Test
  void testTwoArgsConstructor() {
    // Arrange
    String mensagem = "Erro ao validar requisição";
    String codigo = "ERR_VALIDATION_422";

    // Act
    ValidationErrorResponse response = new ValidationErrorResponse(mensagem, codigo);

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertEquals(codigo, response.getCodigo());
    assertNull(response.getErros());
    assertNotNull(response.getTimestamp());
  }

  @Test
  void testBuilderPattern() {
    // Arrange
    String mensagem = "Validação falhou";
    String codigo = "ERR_VAL_001";
    List<FieldError> erros = new ArrayList<>();
    erros.add(new FieldError("nome", "Nome é obrigatório", null));
    ZonedDateTime timestamp = ZonedDateTime.now();

    // Act
    ValidationErrorResponse response = ValidationErrorResponse.builder()
        .mensagem(mensagem)
        .codigo(codigo)
        .erros(erros)
        .timestamp(timestamp)
        .build();

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertEquals(codigo, response.getCodigo());
    assertEquals(erros, response.getErros());
    assertEquals(timestamp, response.getTimestamp());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    ValidationErrorResponse response = new ValidationErrorResponse();
    String mensagem = "Dados inválidos";
    String codigo = "ERR_INVALID_DATA";
    List<FieldError> erros = List.of(new FieldError("cpf", "CPF inválido", "123.456.789-00"));
    ZonedDateTime timestamp = ZonedDateTime.now();

    // Act
    response.setMensagem(mensagem);
    response.setCodigo(codigo);
    response.setErros(erros);
    response.setTimestamp(timestamp);

    // Assert
    assertEquals(mensagem, response.getMensagem());
    assertEquals(codigo, response.getCodigo());
    assertEquals(erros, response.getErros());
    assertEquals(timestamp, response.getTimestamp());
  }

  @Test
  void testWithMultipleFieldErrors() {
    // Arrange
    List<FieldError> erros = List.of(
        new FieldError("email", "Email inválido", "test@"),
        new FieldError("senha", "Senha muito curta", "123"),
        new FieldError("nome", "Nome muito longo", "A".repeat(300))
    );

    // Act
    ValidationErrorResponse response = new ValidationErrorResponse("Múltiplos erros", "ERR_MULTI", erros, ZonedDateTime.now());

    // Assert
    assertEquals(3, response.getErros().size());
    assertEquals("email", response.getErros().get(0).getCampo());
    assertEquals("senha", response.getErros().get(1).getCampo());
    assertEquals("nome", response.getErros().get(2).getCampo());
  }

  @Test
  void testToString() {
    // Arrange
    ValidationErrorResponse response = new ValidationErrorResponse("Erro", "ERR_001");

    // Act
    String toString = response.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("Erro"));
    assertTrue(toString.contains("ERR_001"));
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    ZonedDateTime now = ZonedDateTime.now();
    List<FieldError> erros = List.of(new FieldError("campo", "mensagem", "valor"));
    ValidationErrorResponse response1 = new ValidationErrorResponse("Msg", "CODE", erros, now);
    ValidationErrorResponse response2 = new ValidationErrorResponse("Msg", "CODE", erros, now);
    ValidationErrorResponse response3 = new ValidationErrorResponse("Outra", "OTHER", erros, now);

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
    ValidationErrorResponse response = new ValidationErrorResponse("Teste", "ERR_001");
    ZonedDateTime after = ZonedDateTime.now();

    // Assert
    assertNotNull(response.getTimestamp());
    assertTrue(response.getTimestamp().isAfter(before.minusSeconds(1)));
    assertTrue(response.getTimestamp().isBefore(after.plusSeconds(1)));
  }

  // Tests for FieldError inner class
  @Test
  void testFieldErrorNoArgsConstructor() {
    // Act
    FieldError fieldError = new FieldError();

    // Assert
    assertNotNull(fieldError);
    assertNull(fieldError.getCampo());
    assertNull(fieldError.getMensagem());
    assertNull(fieldError.getValorRejeitado());
  }

  @Test
  void testFieldErrorAllArgsConstructor() {
    // Arrange
    String campo = "email";
    String mensagem = "Formato inválido";
    String valorRejeitado = "invalid@email";

    // Act
    FieldError fieldError = new FieldError(campo, mensagem, valorRejeitado);

    // Assert
    assertNotNull(fieldError);
    assertEquals(campo, fieldError.getCampo());
    assertEquals(mensagem, fieldError.getMensagem());
    assertEquals(valorRejeitado, fieldError.getValorRejeitado());
  }

  @Test
  void testFieldErrorSettersAndGetters() {
    // Arrange
    FieldError fieldError = new FieldError();
    String campo = "telefone";
    String mensagem = "Formato incorreto";
    String valorRejeitado = "123";

    // Act
    fieldError.setCampo(campo);
    fieldError.setMensagem(mensagem);
    fieldError.setValorRejeitado(valorRejeitado);

    // Assert
    assertEquals(campo, fieldError.getCampo());
    assertEquals(mensagem, fieldError.getMensagem());
    assertEquals(valorRejeitado, fieldError.getValorRejeitado());
  }

  @Test
  void testFieldErrorToString() {
    // Arrange
    FieldError fieldError = new FieldError("campo1", "erro1", "valor1");

    // Act
    String toString = fieldError.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("campo1"));
    assertTrue(toString.contains("erro1"));
    assertTrue(toString.contains("valor1"));
  }

  @Test
  void testFieldErrorEqualsAndHashCode() {
    // Arrange
    FieldError error1 = new FieldError("campo", "mensagem", "valor");
    FieldError error2 = new FieldError("campo", "mensagem", "valor");
    FieldError error3 = new FieldError("outro", "outra", "outro");

    // Act & Assert
    assertEquals(error1, error2);
    assertEquals(error1.hashCode(), error2.hashCode());
    assertNotEquals(error1, error3);
    assertNotEquals(error1.hashCode(), error3.hashCode());
  }

  @Test
  void testFieldErrorWithNullValues() {
    // Act
    FieldError fieldError = new FieldError(null, null, null);

    // Assert
    assertNotNull(fieldError);
    assertNull(fieldError.getCampo());
    assertNull(fieldError.getMensagem());
    assertNull(fieldError.getValorRejeitado());
  }

  @Test
  void testValidationErrorResponseWithNullValues() {
    // Act
    ValidationErrorResponse response = new ValidationErrorResponse(null, null, null, null);

    // Assert
    assertNotNull(response);
    assertNull(response.getMensagem());
    assertNull(response.getCodigo());
    assertNull(response.getErros());
    assertNull(response.getTimestamp());
  }

  @Test
  void testEmptyErrorsList() {
    // Arrange
    List<FieldError> emptyList = new ArrayList<>();

    // Act
    ValidationErrorResponse response = new ValidationErrorResponse("Sem erros", "ERR_000", emptyList, ZonedDateTime.now());

    // Assert
    assertNotNull(response.getErros());
    assertTrue(response.getErros().isEmpty());
  }
}
