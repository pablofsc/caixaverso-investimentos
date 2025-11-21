package org.pablofsc.domain.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerfilRiscoResponseTest {

  @Test
  void testNoArgsConstructor() {
    // Act
    PerfilRiscoResponse response = new PerfilRiscoResponse();

    // Assert
    assertNotNull(response);
    assertNull(response.getClienteId());
    assertNull(response.getPerfil());
    assertNull(response.getPontuacao());
    assertNull(response.getDescricao());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Long clienteId = 1L;
    String perfil = "CONSERVADOR";
    Integer pontuacao = 85;
    String descricao = "Perfil conservador com baixa tolerância ao risco";

    // Act
    PerfilRiscoResponse response = new PerfilRiscoResponse(clienteId, perfil, pontuacao, descricao);

    // Assert
    assertNotNull(response);
    assertEquals(clienteId, response.getClienteId());
    assertEquals(perfil, response.getPerfil());
    assertEquals(pontuacao, response.getPontuacao());
    assertEquals(descricao, response.getDescricao());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    PerfilRiscoResponse response = new PerfilRiscoResponse();
    Long clienteId = 2L;
    String perfil = "MODERADO";
    Integer pontuacao = 65;
    String descricao = "Perfil moderado com tolerância média ao risco";

    // Act
    response.setClienteId(clienteId);
    response.setPerfil(perfil);
    response.setPontuacao(pontuacao);
    response.setDescricao(descricao);

    // Assert
    assertEquals(clienteId, response.getClienteId());
    assertEquals(perfil, response.getPerfil());
    assertEquals(pontuacao, response.getPontuacao());
    assertEquals(descricao, response.getDescricao());
  }

  @Test
  void testToString() {
    // Arrange
    PerfilRiscoResponse response = new PerfilRiscoResponse(1L, "AGRESSIVO", 95, "Perfil agressivo");

    // Act
    String toString = response.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("1"));
    assertTrue(toString.contains("AGRESSIVO"));
    assertTrue(toString.contains("95"));
    assertTrue(toString.contains("Perfil agressivo"));
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    PerfilRiscoResponse response1 = new PerfilRiscoResponse(1L, "CONSERVADOR", 80, "Descrição 1");
    PerfilRiscoResponse response2 = new PerfilRiscoResponse(1L, "CONSERVADOR", 80, "Descrição 1");
    PerfilRiscoResponse response3 = new PerfilRiscoResponse(2L, "MODERADO", 70, "Descrição 2");

    // Act & Assert
    assertEquals(response1, response2);
    assertEquals(response1.hashCode(), response2.hashCode());
    assertNotEquals(response1, response3);
    assertNotEquals(response1.hashCode(), response3.hashCode());
  }
}
