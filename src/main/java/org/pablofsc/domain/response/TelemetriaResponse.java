package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.pablofsc.domain.model.Periodo;
import org.pablofsc.domain.model.ServicoTelemetria;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TelemetriaResponse", description = "Resposta com dados de telemetria e monitoramento do sistema")
public class TelemetriaResponse {

  @Schema(description = "Lista de serviços monitorados com suas métricas")
  private List<ServicoTelemetria> servicos;

  @Schema(description = "Período de tempo coberto pelos dados de telemetria")
  private Periodo periodo;
}
