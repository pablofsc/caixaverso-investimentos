package org.pablofsc.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * Contexto de segurança JWT que extrai informações do token autenticado.
 * Fornece acesso fácil aos claims e grupos do usuário.
 */
@RequestScoped
public class JwtSecurityContext {

  @Inject
  JsonWebToken jwt;

  /**
   * Obtém email do usuário autenticado do token JWT.
   *
   * @return Email do usuário
   */
  public String getUserEmail() {
    return jwt.getClaim("email");
  }

  /**
   * Obtém nome do usuário autenticado do token JWT.
   *
   * @return Nome do usuário
   */
  public String getUserName() {
    return jwt.getClaim("nome");
  }

  /**
   * Obtém ID do usuário autenticado do token JWT.
   *
   * @return ID do usuário
   */
  public Long getUserId() {
    return jwt.getClaim("userId");
  }

  /**
   * Obtém subject (claim 'sub') do token JWT.
   *
   * @return Subject do token
   */
  public String getSubject() {
    return jwt.getSubject();
  }

  /**
   * Verifica se usuário possui role especificado.
   * USER pode ser atribuído a ADMIN também.
   *
   * @param role Role a verificar (ex: USER, ADMIN)
   * @return true se usuário possui a role
   */
  public boolean isUserInRole(String role) {
    if ("USER".equals(role)) {
      return jwt.getGroups().contains("USER") || jwt.getGroups().contains("ADMIN");
    }
    return jwt.getGroups().contains(role);
  }
}
