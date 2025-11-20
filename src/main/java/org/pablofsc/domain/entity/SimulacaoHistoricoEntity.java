package org.pablofsc.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "simulacao_historico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimulacaoHistoricoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cliente_id", nullable = false)
  private Long clienteId;

  @Column(nullable = false)
  private String produto;

  @Column(name = "valor_investido")
  private Double valorInvestido;

  @Column(name = "valor_final")
  private Double valorFinal;

  @Column(name = "prazo_meses")
  private Integer prazoMeses;

  @Column(name = "data_simulacao", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime dataSimulacao;
}
