package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.response.SimulacaoPorProdutoDiaResponse;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class SimulacaoPorProdutoDiaService {

  public List<SimulacaoPorProdutoDiaResponse> listarSimulacoesPorProdutoDia() {
    // Dados mockados
    return List.of(
        new SimulacaoPorProdutoDiaResponse(
            "CDB Caixa 2026",
            LocalDate.parse("2025-10-30"),
            15,
            11050.00),
        new SimulacaoPorProdutoDiaResponse(
            "Fundo XPTO",
            LocalDate.parse("2025-10-30"),
            8,
            5700.00));
  }
}
