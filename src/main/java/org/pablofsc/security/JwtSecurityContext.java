package org.pablofsc.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
public class JwtSecurityContext {

  @Inject
  JsonWebToken jwt;

  public String getUserEmail() {
    return jwt.getClaim("email");
  }

  public String getUserName() {
    return jwt.getClaim("nome");
  }

  public Long getUserId() {
    return jwt.getClaim("userId");
  }

  public String getSubject() {
    return jwt.getSubject();
  }

  public boolean isUserInRole(String role) {
    return jwt.getGroups().contains(role);
  }
}
