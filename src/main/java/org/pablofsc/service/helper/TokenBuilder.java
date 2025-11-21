package org.pablofsc.service.helper;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import org.pablofsc.domain.entity.UsuarioEntity;

import java.time.Duration;
import java.time.Instant;

/**
 * Construtor de JWT tokens - isola lógica de geração de tokens.
 * Facilita testes de expiração, claims, assinatura sem precisar de toda a
 * AutenticacaoService.
 */
public class TokenBuilder {

  private final String issuer;
  private final String audience;
  private final Duration expirationDuration;

  public TokenBuilder(String issuer, String audience, Duration expirationDuration) {
    this.issuer = issuer;
    this.audience = audience;
    this.expirationDuration = expirationDuration;
  }

  /**
   * Constrói token JWT para usuário
   */
  public String buildToken(UsuarioEntity usuario) {
    Instant now = Instant.now();
    Instant expiresAt = now.plus(expirationDuration);

    return Jwt.issuer(issuer)
        .upn(usuario.getEmail())
        .subject(usuario.getEmail())
        .groups(usuario.getRole().name())
        .claim("email", usuario.getEmail())
        .claim("nome", usuario.getNome())
        .claim("userId", usuario.getId())
        .claim("role", usuario.getRole().name())
        .audience(audience)
        .issuedAt(now)
        .expiresAt(expiresAt)
        .sign();
  }

  /**
   * Criptografa senha usando Bcrypt
   */
  public String criptografarSenha(String senha) {
    return BcryptUtil.bcryptHash(senha);
  }

  /**
   * Verifica se senha fornecida corresponde à senha criptografada
   */
  public boolean verificarSenha(String senhaFornecida, String senhaCriptografada) {
    return BcryptUtil.matches(senhaFornecida, senhaCriptografada);
  }
}
