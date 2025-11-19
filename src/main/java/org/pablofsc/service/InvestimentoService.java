package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.response.InvestimentoResponse;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class InvestimentoService {

  public List<InvestimentoResponse> obterInvestimentos(Long clienteId) {
    // Dados mockados
    return List.of(
        new InvestimentoResponse(
            1L,
            "CDB",
            5000.00,
            0.12,
            LocalDate.parse("2025-01-15")),
        new InvestimentoResponse(
            2L,
            "Fundo Multimercado",
            3000.00,
            0.08,
            LocalDate.parse("2025-03-10")));
  }
}
