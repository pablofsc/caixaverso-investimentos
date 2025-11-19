package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulacaoInvestimentoResponse {

  private Produto produtoValidado;
  private Simulacao resultadoSimulacao;
  private ZonedDateTime dataSimulacao;
}
