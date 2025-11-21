package org.pablofsc.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um produto de investimento.
 */
@Entity
@Table(name = "produtos")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoEntity extends PanacheEntityBase {

  /**
   * Identificador único do produto de investimento.
   */
  @Id
  private Long id;

  /**
   * Nome do produto de investimento (ex: CDB Caixa 2026).
   */
  @Column(nullable = false)
  private String nome;

  /**
   * Tipo do produto (ex: CDB, Fundo, Renda Fixa).
   */
  @Column(nullable = false)
  private String tipo;

  /**
   * Rentabilidade anual do produto (ex: 0.12 = 12% a.a.).
   */
  private Double rentabilidade;

  /**
   * Nível de risco do produto (ex: "Baixo", "Alto").
   */
  private String risco;
}
