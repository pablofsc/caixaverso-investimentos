package org.pablofsc.service.helper;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import org.pablofsc.domain.entity.UsuarioEntity;

import java.time.Duration;
import java.time.Instant;

/**
 * Construtor de JWT tokens com suporte a criptografia de senhas.
 * Isola lógica de geração e validação de tokens para facilitar testes.
 * Utiliza Bcrypt para hash de senhas e smallrye-jwt para geração de tokens.
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
   * Constrói token JWT com claims do usuário.
   * Token inclui: email, nome, userId, role, e expira em 24 horas.
   *
   * @param usuario Usuário para qual gerar token
   * @return Token JWT assinado
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
   * Criptografa senha usando algoritmo Bcrypt com salt.
   *
   * @param senha Senha em texto plano
   * @return Hash Bcrypt da senha
   */
  public String criptografarSenha(String senha) {
    return BcryptUtil.bcryptHash(senha);
  }

  /**
   * Verifica se senha fornecida corresponde ao hash Bcrypt armazenado.
   *
   * @param senhaFornecida Senha em texto plano fornecida pelo usuário
   * @param senhaCriptografada Hash Bcrypt armazenado no banco de dados
   * @return true se a senha corresponde, false caso contrário
   */
  public boolean verificarSenha(String senhaFornecida, String senhaCriptografada) {
    return BcryptUtil.matches(senhaFornecida, senhaCriptografada);
  }
}
