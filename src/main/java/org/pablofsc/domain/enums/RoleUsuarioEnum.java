package org.pablofsc.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum que representa os papéis de usuário no sistema.
 * USER: Usuário comum com acesso a funcionalidades básicas
 * ADMIN: Administrador com acesso a todas as funcionalidades
 */
@Getter
@AllArgsConstructor
public enum RoleUsuarioEnum {
  USER("user", "Usuário comum"),
  ADMIN("admin", "Administrador");

  private final String valor;
  private final String descricao;

  // Constants para uso em anotações @RolesAllowed
  public static final String USER_ROLE = "USER";
  public static final String ADMIN_ROLE = "ADMIN";
}
