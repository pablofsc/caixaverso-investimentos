package org.pablofsc.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidade que representa investimentos efetivados por clientes.
 * Utilizada para análise de comportamento financeiro e cálculo de perfil de
 * risco.
 */
@Entity
@Table(name = "investimentos")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestimentoEntity extends PanacheEntityBase {

  /**
   * Identificador único do investimento.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Cliente que realizou o investimento.
   */
  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable = false)
  private ClienteEntity cliente;

  /**
   * Produto de investimento escolhido pelo cliente.
   */
  @ManyToOne
  @JoinColumn(name = "produto_id", nullable = false)
  private ProdutoEntity produto;

  /**
   * Valor investido pelo cliente, em reais (R$).
   */
  @Column(nullable = false)
  private Double valor;

  /**
   * Data em que o investimento foi realizado.
   */
  @Column(nullable = false)
  private LocalDate data;
}
