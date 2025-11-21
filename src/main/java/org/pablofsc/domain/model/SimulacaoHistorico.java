package org.pablofsc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulacaoHistorico {

  private Long id;
  private Long clienteId;
  private String produto;
  private Double valorInvestido;
  private Double valorFinal;
  private Integer prazoMeses;
  private ZonedDateTime dataSimulacao;
}
