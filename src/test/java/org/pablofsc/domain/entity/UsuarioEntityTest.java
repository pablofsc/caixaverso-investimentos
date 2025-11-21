package org.pablofsc.domain.entity;

import org.junit.jupiter.api.Test;
import org.pablofsc.domain.enums.RoleUsuarioEnum;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioEntityTest {

  @Test
  void testBuilderCreatesInstanceWithAllFields() {
    // Arrange
    Long id = 1L;
    String email = "user@example.com";
    String senha = "password123";
    String nome = "João Silva";
    RoleUsuarioEnum role = RoleUsuarioEnum.ADMIN;

    // Act
    UsuarioEntity usuario = UsuarioEntity.builder()
        .id(id)
        .email(email)
        .senha(senha)
        .nome(nome)
        .role(role)
        .build();

    // Assert
    assertEquals(id, usuario.getId());
    assertEquals(email, usuario.getEmail());
    assertEquals(senha, usuario.getSenha());
    assertEquals(nome, usuario.getNome());
    assertEquals(role, usuario.getRole());
  }

  @Test
  void testBuilderWithDefaultRole() {
    // Act
    UsuarioEntity usuario = UsuarioEntity.builder()
        .id(1L)
        .email("test@example.com")
        .senha("pass")
        .nome("Test")
        .build();

    // Assert
    assertEquals(RoleUsuarioEnum.USER, usuario.getRole());
  }

  @Test
  void testDefaultConstructor() {
    // Act
    UsuarioEntity usuario = new UsuarioEntity();

    // Assert
    assertNull(usuario.getId());
    assertNull(usuario.getEmail());
    assertNull(usuario.getSenha());
    assertNull(usuario.getNome());
    assertEquals(RoleUsuarioEnum.USER, usuario.getRole()); // Default value from @Builder.Default
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    Long id = 2L;
    String email = "admin@example.com";
    String senha = "adminpass";
    String nome = "Admin User";
    RoleUsuarioEnum role = RoleUsuarioEnum.ADMIN;

    // Act
    UsuarioEntity usuario = new UsuarioEntity(id, email, senha, nome, role);

    // Assert
    assertEquals(id, usuario.getId());
    assertEquals(email, usuario.getEmail());
    assertEquals(senha, usuario.getSenha());
    assertEquals(nome, usuario.getNome());
    assertEquals(role, usuario.getRole());
  }

  @Test
  void testSettersAndGetters() {
    // Arrange
    UsuarioEntity usuario = new UsuarioEntity();

    // Act
    usuario.setId(3L);
    usuario.setEmail("new@example.com");
    usuario.setSenha("newpass");
    usuario.setNome("New User");
    usuario.setRole(RoleUsuarioEnum.USER);

    // Assert
    assertEquals(3L, usuario.getId());
    assertEquals("new@example.com", usuario.getEmail());
    assertEquals("newpass", usuario.getSenha());
    assertEquals("New User", usuario.getNome());
    assertEquals(RoleUsuarioEnum.USER, usuario.getRole());
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    UsuarioEntity user1 = UsuarioEntity.builder()
        .id(1L)
        .email("test@example.com")
        .senha("pass")
        .nome("Test")
        .role(RoleUsuarioEnum.USER)
        .build();

    UsuarioEntity user2 = UsuarioEntity.builder()
        .id(1L)
        .email("test@example.com")
        .senha("pass")
        .nome("Test")
        .role(RoleUsuarioEnum.USER)
        .build();

    UsuarioEntity user3 = UsuarioEntity.builder()
        .id(2L)
        .email("other@example.com")
        .build();

    // Assert
    assertEquals(user1, user2);
    assertEquals(user1.hashCode(), user2.hashCode());
    assertNotEquals(user1, user3);
    assertNotEquals(user1.hashCode(), user3.hashCode());
  }

  @Test
  void testEqualsWithNull() {
    // Arrange
    UsuarioEntity usuario = UsuarioEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(usuario.equals(null));
  }

  @Test
  void testEqualsWithDifferentType() {
    // Arrange
    UsuarioEntity usuario = UsuarioEntity.builder().id(1L).build();

    // Act & Assert
    assertFalse(usuario.equals("string"));
  }

  @Test
  void testEqualsWithSameInstance() {
    // Arrange
    UsuarioEntity usuario = UsuarioEntity.builder().id(1L).build();

    // Act & Assert
    assertTrue(usuario.equals(usuario));
  }

  @Test
  void testEqualsAndHashCodeWithNullFields() {
    // Arrange
    UsuarioEntity user1 = UsuarioEntity.builder()
        .id(null)
        .email(null)
        .senha(null)
        .nome(null)
        .role(null)
        .build();

    UsuarioEntity user2 = UsuarioEntity.builder()
        .id(null)
        .email(null)
        .senha(null)
        .nome(null)
        .role(null)
        .build();

    UsuarioEntity user3 = UsuarioEntity.builder()
        .id(1L)
        .email(null)
        .build();

    // Assert
    assertEquals(user1, user2);
    assertEquals(user1.hashCode(), user2.hashCode());
    assertNotEquals(user1, user3);
    assertNotEquals(user1.hashCode(), user3.hashCode());
  }

  @Test
  void testToString() {
    // Arrange
    UsuarioEntity usuario = UsuarioEntity.builder()
        .id(1L)
        .email("test@example.com")
        .nome("Test")
        .build();

    // Act
    String toString = usuario.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("UsuarioEntity"));
    assertTrue(toString.contains("id=1"));
    assertTrue(toString.contains("email=test@example.com"));
    assertTrue(toString.contains("nome=Test"));
  }

  @Test
  void testWithNullValues() {
    // Act
    UsuarioEntity usuario = UsuarioEntity.builder()
        .id(null)
        .email(null)
        .senha(null)
        .nome(null)
        .role(null)
        .build();

    // Assert
    assertNull(usuario.getId());
    assertNull(usuario.getEmail());
    assertNull(usuario.getSenha());
    assertNull(usuario.getNome());
    assertNull(usuario.getRole());
  }

  @Test
  void testWithExtremeValues() {
    // Arrange
    String longEmail = "a".repeat(254) + "@example.com"; // Assuming max email length
    String longNome = "a".repeat(100);
    String longSenha = "a".repeat(128);

    // Act
    UsuarioEntity usuario = UsuarioEntity.builder()
        .email(longEmail)
        .nome(longNome)
        .senha(longSenha)
        .build();

    // Assert
    assertEquals(longEmail, usuario.getEmail());
    assertEquals(longNome, usuario.getNome());
    assertEquals(longSenha, usuario.getSenha());
  }
}
