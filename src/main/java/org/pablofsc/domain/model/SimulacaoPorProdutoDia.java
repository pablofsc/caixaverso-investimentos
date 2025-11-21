package org.pablofsc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulacaoPorProdutoDia {

  private String produto;
  private LocalDate data;
  private Integer quantidadeSimulacoes;
  private Double mediaValorFinal;
}
