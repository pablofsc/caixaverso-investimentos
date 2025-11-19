package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.model.Periodo;
import org.pablofsc.domain.model.ServicoTelemetria;
import org.pablofsc.domain.response.TelemetriaResponse;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TelemetriaService {

  public TelemetriaResponse obterTelemetria() {
    // Dados mockados
    List<ServicoTelemetria> servicos = List.of(
        new ServicoTelemetria(
            "simular-investimento",
            120,
            250),
        new ServicoTelemetria(
            "perfil-risco",
            80,
            180));

    Periodo periodo = new Periodo(
        LocalDate.parse("2025-10-01"),
        LocalDate.parse("2025-10-31"));

    return new TelemetriaResponse(servicos, periodo);
  }
}
