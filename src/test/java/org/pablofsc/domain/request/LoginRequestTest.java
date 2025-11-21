package org.pablofsc.domain.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

  @Test
  void testNoArgsConstructor() {
    // Act
    LoginRequest request = new LoginRequest();

    // Assert
    assertNotNull(request);
    assertNull(request.getEmail());
    assertNull(request.getSenha());
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    String email = "usuario@teste.com";
    String senha = "senha123";

    // Act
    LoginRequest request = new LoginRequest(email, senha);

    // Assert
    assertNotNull(request);
    assertEquals(email, request.getEmail());
    assertEquals(senha, request.getSenha());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    LoginRequest request = new LoginRequest();
    String email = "maria@teste.com";
    String senha = "senha456";

    // Act
    request.setEmail(email);
    request.setSenha(senha);

    // Assert
    assertEquals(email, request.getEmail());
    assertEquals(senha, request.getSenha());
  }

  @Test
  void testToString() {
    // Arrange
    LoginRequest request = new LoginRequest("user@test.com", "password123");

    // Act
    String toString = request.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("user@test.com"));
    assertTrue(toString.contains("password123"));
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    LoginRequest request1 = new LoginRequest("email1@test.com", "senha1");
    LoginRequest request2 = new LoginRequest("email1@test.com", "senha1");
    LoginRequest request3 = new LoginRequest("email2@test.com", "senha2");

    // Act & Assert
    assertEquals(request1, request2);
    assertEquals(request1.hashCode(), request2.hashCode());
    assertNotEquals(request1, request3);
    assertNotEquals(request1.hashCode(), request3.hashCode());
  }
}
