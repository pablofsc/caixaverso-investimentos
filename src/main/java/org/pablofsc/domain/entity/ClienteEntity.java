package org.pablofsc.domain.entity;

import org.pablofsc.domain.enums.FrequenciaMovimentacoesEnum;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.PreferenciaRentLiqEnum;

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
 * Entidade que representa um cliente e seu perfil de investimentos.
 * Armazena preferências, comportamento financeiro e tolerância a risco.
 */
@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteEntity extends PanacheEntityBase {

  /**
   * Identificador único do cliente.
   */
  @Id
  private Long id;

  /**
   * Nome completo do cliente.
   */
  @Column(nullable = false)
  private String nome;

  // Perfil do cliente
  /**
   * Prazo médio preferido para investimentos, em meses (ex: 12 = um ano).
   */
  @Column(name = "prazo_medio_preferido")
  private Integer prazoMedioPreferido;

  /**
   * Preferência do cliente entre liquidez, equilíbrio ou rentabilidade.
   */
  @Column(name = "preferencia_liquidez_rentabilidade")
  @Enumerated(EnumType.STRING)
  private PreferenciaRentLiqEnum preferenciaRentLiq;

  /**
   * Nível máximo de risco aceitável pelo cliente.
   */
  @Column(name = "risco_maximo_aceitavel")
  @Enumerated(EnumType.STRING)
  private NivelRiscoEnum riscoMaximoAceitavel;

  /**
   * Volume total investido pelo cliente, em reais (R$).
   */
  @Column(name = "volume_total_investido")
  private Double volumeTotalInvestido;

  /**
   * Frequência de movimentações financeiras do cliente.
   */
  @Column(name = "frequencia_movimentacoes")
  @Enumerated(EnumType.STRING)
  private FrequenciaMovimentacoesEnum frequenciaMovimentacoes;
}
