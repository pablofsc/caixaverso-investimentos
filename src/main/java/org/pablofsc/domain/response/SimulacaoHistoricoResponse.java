package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulacaoHistoricoResponse {

  private Long id;
  private Long clienteId;
  private String produto;
  private Double valorInvestido;
  private Double valorFinal;
  private Integer prazoMeses;
  private ZonedDateTime dataSimulacao;
}
