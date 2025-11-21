package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SimulacaoInvestimentoResponse", description = "Resposta contendo resultado completo da simulação de investimento")
public class SimulacaoInvestimentoResponse {

  @Schema(description = "Produto de investimento validado para a simulação")
  private Produto produtoValidado;

  @Schema(description = "Resultado da simulação com cálculos de rentabilidade")
  private Simulacao resultadoSimulacao;

  @Schema(description = "Data e hora em que a simulação foi realizada no formato ISO-8601")
  private ZonedDateTime dataSimulacao;
}
