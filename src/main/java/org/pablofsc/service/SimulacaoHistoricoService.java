package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.response.SimulacaoHistoricoResponse;

import java.time.ZonedDateTime;
import java.util.List;

@ApplicationScoped
public class SimulacaoHistoricoService {

  public List<SimulacaoHistoricoResponse> listarSimulacoes() {
    // Dados mockados
    return List.of(
        new SimulacaoHistoricoResponse(
            1L,
            123L,
            "CDB Caixa 2026",
            10000.00,
            11200.00,
            12,
            ZonedDateTime.parse("2025-10-31T14:00:00Z")),
        new SimulacaoHistoricoResponse(
            2L,
            123L,
            "Fundo XPTO",
            5000.00,
            5800.00,
            6,
            ZonedDateTime.parse("2025-09-15T10:30:00Z")));
  }
}
