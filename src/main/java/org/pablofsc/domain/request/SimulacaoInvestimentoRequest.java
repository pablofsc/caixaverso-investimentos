package org.pablofsc.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulacaoInvestimentoRequest {

  private Long clienteId;
  private Double valor;
  private Integer prazoMeses;
  private String tipoProduto;
}
