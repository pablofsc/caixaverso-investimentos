package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pablofsc.domain.model.Periodo;
import org.pablofsc.domain.model.ServicoTelemetria;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelemetriaResponse {

  private List<ServicoTelemetria> servicos;
  private Periodo periodo;
}
