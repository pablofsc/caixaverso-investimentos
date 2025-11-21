package org.pablofsc.domain.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

  @Test
  void testNoArgsConstructor() {
    // Act
    LoginResponse response = new LoginResponse();

    // Assert
    assertNotNull(response);
    assertNull(response.getToken());
    assertNull(response.getEmail());
    assertNull(response.getNome());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    String token = "jwt-token-123";
    String email = "usuario@teste.com";
    String nome = "João Silva";

    // Act
    LoginResponse response = new LoginResponse(token, email, nome);

    // Assert
    assertNotNull(response);
    assertEquals(token, response.getToken());
    assertEquals(email, response.getEmail());
    assertEquals(nome, response.getNome());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    LoginResponse response = new LoginResponse();
    String token = "jwt-token-456";
    String email = "maria@teste.com";
    String nome = "Maria Santos";

    // Act
    response.setToken(token);
    response.setEmail(email);
    response.setNome(nome);

    // Assert
    assertEquals(token, response.getToken());
    assertEquals(email, response.getEmail());
    assertEquals(nome, response.getNome());
  }

  @Test
  void testToString() {
    // Arrange
    LoginResponse response = new LoginResponse("token123", "user@test.com", "User Name");

    // Act
    String toString = response.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("token123"));
    assertTrue(toString.contains("user@test.com"));
    assertTrue(toString.contains("User Name"));
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    LoginResponse response1 = new LoginResponse("token1", "email1@test.com", "Name1");
    LoginResponse response2 = new LoginResponse("token1", "email1@test.com", "Name1");
    LoginResponse response3 = new LoginResponse("token2", "email2@test.com", "Name2");

    // Act & Assert
    assertEquals(response1, response2);
    assertEquals(response1.hashCode(), response2.hashCode());
    assertNotEquals(response1, response3);
    assertNotEquals(response1.hashCode(), response3.hashCode());
  }
}
