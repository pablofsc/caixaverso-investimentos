package org.pablofsc.service.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.UsuarioEntity;
import org.pablofsc.domain.enums.RoleUsuarioEnum;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TokenBuilderTest {

  private TokenBuilder tokenBuilder;

  @BeforeEach
  void setUp() {
    // Configuração típica para testes
    String issuer = "caixaverso-test";
    String audience = "caixaverso-users";
    Duration expirationDuration = Duration.ofHours(1);

    tokenBuilder = new TokenBuilder(issuer, audience, expirationDuration);
  }

  @Test
  void testBuildToken() {
    // Arrange
    UsuarioEntity usuario = UsuarioEntity.builder()
        .id(1L)
        .email("usuario@teste.com")
        .nome("Usuário Teste")
        .role(RoleUsuarioEnum.USER)
        .build();

    // Act
    String token = tokenBuilder.buildToken(usuario);

    // Assert
    assertNotNull(token);
    assertFalse(token.isEmpty());
    // JWT tokens têm 3 partes separadas por pontos
    assertEquals(3, token.split("\\.").length);
    // Deve ser um token relativamente longo
    assertTrue(token.length() > 100);
  }

  @Test
  void testBuildTokenComUsuarioAdmin() {
    // Arrange
    UsuarioEntity usuario = UsuarioEntity.builder()
        .id(2L)
        .email("admin@teste.com")
        .nome("Admin Teste")
        .role(RoleUsuarioEnum.ADMIN)
        .build();

    // Act
    String token = tokenBuilder.buildToken(usuario);

    // Assert
    assertNotNull(token);
    assertFalse(token.isEmpty());
    assertEquals(3, token.split("\\.").length);
  }

  @Test
  void testCriptografarSenha() {
    // Arrange
    String senha = "senha123";

    // Act
    String hash = tokenBuilder.criptografarSenha(senha);

    // Assert
    assertNotNull(hash);
    assertFalse(hash.isEmpty());
    // Bcrypt hashes começam com $2a$ ou $2b$ ou $2y$
    assertTrue(hash.startsWith("$2"));
    // Deve ser diferente da senha original
    assertNotEquals(senha, hash);
  }

  @Test
  void testCriptografarSenha_GeraHashesDiferentes() {
    // Arrange
    String senha = "senha123";

    // Act
    String hash1 = tokenBuilder.criptografarSenha(senha);
    String hash2 = tokenBuilder.criptografarSenha(senha);

    // Assert
    assertNotNull(hash1);
    assertNotNull(hash2);
    // Mesmo com mesma senha, hashes devem ser diferentes (salting)
    assertNotEquals(hash1, hash2);
  }

  @Test
  void testVerificarSenha_SenhaCorreta() {
    // Arrange
    String senha = "senha123";
    String hash = tokenBuilder.criptografarSenha(senha);

    // Act
    boolean resultado = tokenBuilder.verificarSenha(senha, hash);

    // Assert
    assertTrue(resultado);
  }

  @Test
  void testVerificarSenha_SenhaIncorreta() {
    // Arrange
    String senhaCorreta = "senha123";
    String senhaIncorreta = "senha456";
    String hash = tokenBuilder.criptografarSenha(senhaCorreta);

    // Act
    boolean resultado = tokenBuilder.verificarSenha(senhaIncorreta, hash);

    // Assert
    assertFalse(resultado);
  }

  @Test
  void testVerificarSenha_HashDiferente() {
    // Arrange
    String senha1 = "senha123";
    String senha2 = "senha456";
    String hash = tokenBuilder.criptografarSenha(senha1);

    // Act
    boolean resultado = tokenBuilder.verificarSenha(senha2, hash);

    // Assert
    assertFalse(resultado);
  }

  @Test
  void testVerificarSenha_SenhaNull() {
    // Arrange
    String hash = tokenBuilder.criptografarSenha("senha123");

    // Act & Assert
    assertThrows(NullPointerException.class,
        () -> tokenBuilder.verificarSenha(null, hash));
  }

  @Test
  void testVerificarSenha_HashNull() {
    // Arrange
    String senha = "senha123";

    // Act & Assert
    assertThrows(NullPointerException.class,
        () -> tokenBuilder.verificarSenha(senha, null));
  }
}
