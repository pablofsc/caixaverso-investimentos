package org.pablofsc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.UsuarioEntity;
import org.pablofsc.domain.enums.RoleUsuarioEnum;
import org.pablofsc.domain.request.LoginRequest;
import org.pablofsc.domain.response.LoginResponse;
import org.pablofsc.repository.UsuarioRepository;
import org.pablofsc.service.helper.TokenBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AutenticacaoServiceTest {

  private UsuarioRepository usuarioRepository;
  private TokenBuilder tokenBuilder;
  private AutenticacaoService autenticacaoService;

  @BeforeEach
  void setUp() {
    usuarioRepository = mock(UsuarioRepository.class);
    tokenBuilder = mock(TokenBuilder.class);
    autenticacaoService = new AutenticacaoService(usuarioRepository, tokenBuilder);
  }

  @Test
  void testAutenticarUsuarioExistenteSenhaCorreta() {
    // Arrange
    LoginRequest request = new LoginRequest("user@test.com", "password123");
    UsuarioEntity usuario = UsuarioEntity.builder()
        .id(1L)
        .email("user@test.com")
        .nome("Test User")
        .senha("hashedPassword")
        .role(RoleUsuarioEnum.USER)
        .build();

    when(usuarioRepository.findByEmail("user@test.com")).thenReturn(Optional.of(usuario));
    when(tokenBuilder.verificarSenha("password123", "hashedPassword")).thenReturn(true);
    when(tokenBuilder.buildToken(usuario)).thenReturn("jwt-token-123");

    // Act
    LoginResponse response = autenticacaoService.autenticar(request);

    // Assert
    assertNotNull(response);
    assertEquals("jwt-token-123", response.getToken());
    assertEquals("user@test.com", response.getEmail());
    assertEquals("Test User", response.getNome());
    verify(usuarioRepository).findByEmail("user@test.com");
    verify(tokenBuilder).verificarSenha("password123", "hashedPassword");
    verify(tokenBuilder).buildToken(usuario);
  }

  @Test
  void testAutenticarUsuarioNaoEncontrado() {
    // Arrange
    LoginRequest request = new LoginRequest("notfound@test.com", "password123");
    when(usuarioRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      autenticacaoService.autenticar(request);
    });
    assertEquals("Usuário não encontrado", exception.getMessage());
    verify(usuarioRepository).findByEmail("notfound@test.com");
    verify(tokenBuilder, never()).verificarSenha(any(), any());
  }

  @Test
  void testAutenticarSenhaInvalida() {
    // Arrange
    LoginRequest request = new LoginRequest("user@test.com", "wrongpassword");
    UsuarioEntity usuario = UsuarioEntity.builder()
        .id(1L)
        .email("user@test.com")
        .nome("Test User")
        .senha("hashedPassword")
        .role(RoleUsuarioEnum.USER)
        .build();

    when(usuarioRepository.findByEmail("user@test.com")).thenReturn(Optional.of(usuario));
    when(tokenBuilder.verificarSenha("wrongpassword", "hashedPassword")).thenReturn(false);

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      autenticacaoService.autenticar(request);
    });
    assertEquals("Senha inválida", exception.getMessage());
    verify(usuarioRepository).findByEmail("user@test.com");
    verify(tokenBuilder).verificarSenha("wrongpassword", "hashedPassword");
    verify(tokenBuilder, never()).buildToken(any());
  }

  @Test
  void testRegistrarUsuarioNovo() {
    // Arrange
    LoginRequest request = new LoginRequest("newuser@test.com", "password123");

    when(usuarioRepository.findByEmail("newuser@test.com")).thenReturn(Optional.empty());
    when(tokenBuilder.criptografarSenha("password123")).thenReturn("hashedPassword123");
    doNothing().when(usuarioRepository).persist(any(UsuarioEntity.class));

    // Act
    UsuarioEntity result = autenticacaoService.registrar(request);

    // Assert
    assertNotNull(result);
    assertEquals("newuser@test.com", result.getEmail());
    assertEquals("newuser", result.getNome());
    assertEquals("hashedPassword123", result.getSenha());
    assertEquals(RoleUsuarioEnum.USER, result.getRole());
    verify(usuarioRepository).findByEmail("newuser@test.com");
    verify(tokenBuilder).criptografarSenha("password123");
    verify(usuarioRepository).persist(any(UsuarioEntity.class));
  }

  @Test
  void testRegistrarEmailJaCadastrado() {
    // Arrange
    LoginRequest request = new LoginRequest("existing@test.com", "password123");
    UsuarioEntity usuarioExistente = UsuarioEntity.builder()
        .id(1L)
        .email("existing@test.com")
        .nome("Existing User")
        .senha("hashedPassword")
        .role(RoleUsuarioEnum.USER)
        .build();

    when(usuarioRepository.findByEmail("existing@test.com")).thenReturn(Optional.of(usuarioExistente));

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      autenticacaoService.registrar(request);
    });
    assertEquals("Email já cadastrado", exception.getMessage());
    verify(usuarioRepository).findByEmail("existing@test.com");
    verify(tokenBuilder, never()).criptografarSenha(any());
    verify(usuarioRepository, never()).persist(any(UsuarioEntity.class));
  }
}
