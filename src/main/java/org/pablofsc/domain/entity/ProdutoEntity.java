package org.pablofsc.domain.entity;

import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
   * Tipo do produto.
   */
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoProdutoEnum tipo;

  /**
   * Rentabilidade anual do produto (ex: 0.12 = 12% a.a.).
   */
  private Double rentabilidade;

  /**
   * Nível de risco do produto.
   */
  @Enumerated(EnumType.STRING)
  private NivelRiscoEnum risco;
}
