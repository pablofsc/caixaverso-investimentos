package org.pablofsc.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade que registra métricas de telemetria dos endpoints da API.
 * Armazena volumes de chamadas e tempos de resposta para análise de desempenho.
 */
@Entity
@Table(name = "telemetrias")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelemetriaEntity extends PanacheEntityBase {

  /**
   * Identificador único da métrica de telemetria.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Endpoint da API monitorado.
   */
  @Column(nullable = false)
  private String endpoint;

  /**
   * Data e hora do registro da métrica.
   */
  @Column(nullable = false)
  private LocalDateTime timestamp;

  /**
   * Tempo de resposta da requisição, em milissegundos.
   */
  @Column(nullable = false)
  private Long tempoRespostaMs;
}
