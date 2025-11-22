package org.pablofsc.domain.response;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SuccessResponseTest {

  @Test
  void testNoArgsConstructor() {
    // Act
    SuccessResponse<String> response = new SuccessResponse<>();

    // Assert
    assertNotNull(response);
    assertNull(response.getMensagem());
    assertNull(response.getData());
    assertNull(response.getTimestamp());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    String mensagem = "Operação realizada com sucesso";
    String data = "Dados de teste";
    ZonedDateTime timestamp = ZonedDateTime.now();

    // Act
    SuccessResponse<String> response = new SuccessResponse<>(mensagem, data, timestamp);

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertEquals(data, response.getData());
    assertEquals(timestamp, response.getTimestamp());
  }

  @Test
  void testTwoArgsConstructor() {
    // Arrange
    String mensagem = "Cadastro criado com sucesso";
    Integer data = 12345;

    // Act
    SuccessResponse<Integer> response = new SuccessResponse<>(mensagem, data);

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertEquals(data, response.getData());
    assertNotNull(response.getTimestamp());
  }

  @Test
  void testBuilderPattern() {
    // Arrange
    String mensagem = "Dados recuperados";
    List<String> data = List.of("item1", "item2", "item3");
    ZonedDateTime timestamp = ZonedDateTime.now();

    // Act
    SuccessResponse<List<String>> response = SuccessResponse.<List<String>>builder()
        .mensagem(mensagem)
        .data(data)
        .timestamp(timestamp)
        .build();

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertEquals(data, response.getData());
    assertEquals(timestamp, response.getTimestamp());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    SuccessResponse<Map<String, Object>> response = new SuccessResponse<>();
    String mensagem = "Atualização bem-sucedida";
    Map<String, Object> data = Map.of("id", 1, "nome", "Teste");
    ZonedDateTime timestamp = ZonedDateTime.now();

    // Act
    response.setMensagem(mensagem);
    response.setData(data);
    response.setTimestamp(timestamp);

    // Assert
    assertEquals(mensagem, response.getMensagem());
    assertEquals(data, response.getData());
    assertEquals(timestamp, response.getTimestamp());
  }

  @Test
  void testWithStringData() {
    // Arrange
    String mensagem = "Mensagem de sucesso";
    String data = "Conteúdo de resposta";

    // Act
    SuccessResponse<String> response = new SuccessResponse<>(mensagem, data);

    // Assert
    assertEquals(mensagem, response.getMensagem());
    assertEquals(data, response.getData());
  }

  @Test
  void testWithIntegerData() {
    // Arrange
    String mensagem = "ID gerado";
    Integer data = 999;

    // Act
    SuccessResponse<Integer> response = new SuccessResponse<>(mensagem, data);

    // Assert
    assertEquals(mensagem, response.getMensagem());
    assertEquals(data, response.getData());
  }

  @Test
  void testWithListData() {
    // Arrange
    String mensagem = "Lista de resultados";
    List<String> data = List.of("resultado1", "resultado2");

    // Act
    SuccessResponse<List<String>> response = new SuccessResponse<>(mensagem, data);

    // Assert
    assertEquals(mensagem, response.getMensagem());
    assertEquals(data, response.getData());
    assertEquals(2, response.getData().size());
  }

  @Test
  void testWithMapData() {
    // Arrange
    String mensagem = "Configurações carregadas";
    Map<String, String> data = Map.of("config1", "value1", "config2", "value2");

    // Act
    SuccessResponse<Map<String, String>> response = new SuccessResponse<>(mensagem, data);

    // Assert
    assertEquals(mensagem, response.getMensagem());
    assertEquals(data, response.getData());
    assertEquals(2, response.getData().size());
  }

  @Test
  void testToString() {
    // Arrange
    SuccessResponse<String> response = new SuccessResponse<>("Sucesso", "Dados");

    // Act
    String toString = response.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("Sucesso"));
    assertTrue(toString.contains("Dados"));
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    ZonedDateTime now = ZonedDateTime.now();
    SuccessResponse<String> response1 = new SuccessResponse<>("Mensagem", "Dados", now);
    SuccessResponse<String> response2 = new SuccessResponse<>("Mensagem", "Dados", now);
    SuccessResponse<String> response3 = new SuccessResponse<>("Outra mensagem", "Outros dados", now);

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
    SuccessResponse<String> response = new SuccessResponse<>("Teste", "Dados");
    ZonedDateTime after = ZonedDateTime.now();

    // Assert
    assertNotNull(response.getTimestamp());
    assertTrue(response.getTimestamp().isAfter(before.minusSeconds(1)));
    assertTrue(response.getTimestamp().isBefore(after.plusSeconds(1)));
  }

  @Test
  void testWithNullData() {
    // Arrange
    String mensagem = "Sem dados";

    // Act
    SuccessResponse<String> response = new SuccessResponse<>(mensagem, null);

    // Assert
    assertNotNull(response);
    assertEquals(mensagem, response.getMensagem());
    assertNull(response.getData());
    assertNotNull(response.getTimestamp());
  }

  @Test
  void testWithNullValues() {
    // Act
    SuccessResponse<String> response = new SuccessResponse<>(null, null, null);

    // Assert
    assertNotNull(response);
    assertNull(response.getMensagem());
    assertNull(response.getData());
    assertNull(response.getTimestamp());
  }

  @Test
  void testBuilderWithPartialData() {
    // Act
    SuccessResponse<String> response = SuccessResponse.<String>builder()
        .mensagem("Apenas mensagem")
        .build();

    // Assert
    assertNotNull(response);
    assertEquals("Apenas mensagem", response.getMensagem());
    assertNull(response.getData());
    assertNull(response.getTimestamp());
  }
}
