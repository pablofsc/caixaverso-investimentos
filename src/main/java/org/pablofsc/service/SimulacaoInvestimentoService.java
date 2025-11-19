package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;
import org.pablofsc.domain.response.SimulacaoInvestimentoResponse;

import java.time.ZonedDateTime;

@ApplicationScoped
public class SimulacaoInvestimentoService {

  public SimulacaoInvestimentoResponse simularInvestimento(SimulacaoInvestimentoRequest request) {
    // Dados mockados
    Produto produtoValidado = new Produto(
        101L,
        "CDB Caixa 2026",
        "CDB",
        0.12,
        "Baixo");

    Simulacao resultadoSimulacao = new Simulacao(
        11200.00,
        0.12,
        12);

    ZonedDateTime dataSimulacao = ZonedDateTime.parse("2025-10-31T14:00:00Z");

    return new SimulacaoInvestimentoResponse(
        produtoValidado,
        resultadoSimulacao,
        dataSimulacao);
  }
}
