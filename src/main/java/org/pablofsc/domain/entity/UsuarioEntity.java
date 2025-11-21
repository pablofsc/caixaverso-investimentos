package org.pablofsc.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.pablofsc.domain.enums.RoleUsuarioEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um usuário do sistema.
 * Utilizada para autenticação e controle de acesso (roles: user, admin).
 */
@Entity
@Table(name = "usuarios")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity extends PanacheEntityBase {

  /**
   * Identificador único do usuário.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * E-mail do usuário para autenticação.
   */
  @Column(nullable = false)
  private String email;

  /**
   * Senha do usuário (criptografada).
   */
  @Column(nullable = false)
  private String senha;

  /**
   * Nome completo do usuário.
   */
  @Column(nullable = false)
  private String nome;

  /**
   * Papel do usuário no sistema.
   */
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private RoleUsuarioEnum role = RoleUsuarioEnum.USER;
}
