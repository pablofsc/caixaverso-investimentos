package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.UsuarioEntity;
import org.pablofsc.domain.enums.RoleUsuarioEnum;
import org.pablofsc.domain.request.LoginRequest;
import org.pablofsc.domain.response.LoginResponse;
import org.pablofsc.repository.UsuarioRepository;
import org.pablofsc.service.helper.TokenBuilder;

import java.time.Duration;

/**
 * Serviço de autenticação e autorização de usuários.
 * Responsável por validar credenciais e gerar tokens JWT para acesso.
 */
@ApplicationScoped
public class AutenticacaoService {

  private final UsuarioRepository usuarioRepository;
  private final TokenBuilder tokenBuilder;

  @Inject
  public AutenticacaoService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
    this.tokenBuilder = new TokenBuilder(
        "caixaverso-investimentos",
        "caixaverso-investimentos",
        Duration.ofHours(24));
  }

  // Construtor para testes
  public AutenticacaoService(UsuarioRepository usuarioRepository, TokenBuilder tokenBuilder) {
    this.usuarioRepository = usuarioRepository;
    this.tokenBuilder = tokenBuilder;
  }

  /**
   * Autentica usuário validando email e senha.
   * Gera token JWT válido por 24 horas após validação bem-sucedida.
   *
   * @param request Requisição contendo email e senha
   * @return Resposta com token JWT, email e nome do usuário
   * @throws RuntimeException Se email não encontrado ou senha inválida
   */
  public LoginResponse autenticar(LoginRequest request) {
    UsuarioEntity usuario = usuarioRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (!tokenBuilder.verificarSenha(request.getSenha(), usuario.getSenha())) {
      throw new RuntimeException("Senha inválida");
    }

    String token = tokenBuilder.buildToken(usuario);

    return new LoginResponse(token, usuario.getEmail(), usuario.getNome());
  }

  /**
   * Registra novo usuário no sistema.
   * Criptografa senha e atribui role padrão (USER).
   *
   * @param request Requisição contendo email e senha
   * @return Entidade do usuário criado
   * @throws RuntimeException Se email já foi cadastrado
   */
  @Transactional
  public UsuarioEntity registrar(LoginRequest request) {
    if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new RuntimeException("Email já cadastrado");
    }

    UsuarioEntity usuario = UsuarioEntity.builder()
        .email(request.getEmail())
        .senha(tokenBuilder.criptografarSenha(request.getSenha()))
        .nome(request.getEmail().split("@")[0])
        .role(RoleUsuarioEnum.USER)
        .build();

    usuarioRepository.persist(usuario);
    return usuario;
  }
}
