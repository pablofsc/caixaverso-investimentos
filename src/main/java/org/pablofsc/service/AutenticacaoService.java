package org.pablofsc.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.UsuarioEntity;
import org.pablofsc.domain.enums.RoleUsuarioEnum;
import org.pablofsc.domain.request.LoginRequest;
import org.pablofsc.domain.response.LoginResponse;
import org.pablofsc.repository.UsuarioRepository;

import java.time.Duration;
import java.time.Instant;

@ApplicationScoped
public class AutenticacaoService {

  @Inject
  UsuarioRepository usuarioRepository;

  public LoginResponse autenticar(LoginRequest request) {
    UsuarioEntity usuario = usuarioRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (!verificarSenha(request.getSenha(), usuario.getSenha())) {
      throw new RuntimeException("Senha inválida");
    }

    String token = gerarToken(usuario);

    return new LoginResponse(token, usuario.getEmail(), usuario.getNome());
  }

  @Transactional
  public UsuarioEntity registrar(LoginRequest request) {
    if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new RuntimeException("Email já cadastrado");
    }

    UsuarioEntity usuario = UsuarioEntity.builder()
        .email(request.getEmail())
        .senha(criptografarSenha(request.getSenha()))
        .nome(request.getEmail().split("@")[0])
        .role(RoleUsuarioEnum.USER)
        .build();

    usuarioRepository.persist(usuario);
    return usuario;
  }

  private String gerarToken(UsuarioEntity usuario) {
    Instant now = Instant.now();
    Instant expiresAt = now.plus(Duration.ofHours(24));

    return Jwt.issuer("caixaverso-investimentos")
        .upn(usuario.getEmail())
        .subject(usuario.getEmail())
        .groups(usuario.getRole().name())
        .claim("email", usuario.getEmail())
        .claim("nome", usuario.getNome())
        .claim("userId", usuario.getId())
        .claim("role", usuario.getRole().name())
        .audience("caixaverso-investimentos")
        .issuedAt(now)
        .expiresAt(expiresAt)
        .sign();
  }

  private String criptografarSenha(String senha) {
    return BcryptUtil.bcryptHash(senha);
  }

  private boolean verificarSenha(String senhaFornecida, String senhaCriptografada) {
    return BcryptUtil.matches(senhaFornecida, senhaCriptografada);
  }
}
