package org.pablofsc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoTelemetria {

  private String nome;
  private Integer quantidadeChamadas;
  private Integer mediaTempoRespostaMs;
}
