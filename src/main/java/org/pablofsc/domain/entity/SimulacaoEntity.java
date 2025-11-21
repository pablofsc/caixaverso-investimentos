package org.pablofsc.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
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

import java.time.ZonedDateTime;

/**
 * Entidade que armazena o histórico de simulações de investimento realizadas.
 * Registra cliente, produto, valores e resultado da simulação.
 */
@Entity
@Table(name = "simulacoes")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimulacaoEntity extends PanacheEntityBase {

  /**
   * Identificador único da simulação.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "INTEGER PRIMARY KEY AUTOINCREMENT")
  private Long id;

  /**
   * Cliente que realizou a simulação.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_simulacao_cliente"))
  private ClienteEntity cliente;

  /**
   * Produto de investimento simulado.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_simulacao_produto"))
  private ProdutoEntity produto;

  /**
   * Valor investido na simulação, em reais (R$).
   */
  @Column(name = "valor_investido")
  private Double valorInvestido;

  /**
   * Valor final obtido na simulação, em reais (R$).
   */
  @Column(name = "valor_final")
  private Double valorFinal;

  /**
   * Prazo da simulação, em meses.
   */
  @Column(name = "prazo_meses")
  private Integer prazoMeses;

  /**
   * Data e hora em que a simulação foi realizada.
   */
  @Column(name = "data_simulacao", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime dataSimulacao;
}
