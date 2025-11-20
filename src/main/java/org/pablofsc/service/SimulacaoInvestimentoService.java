package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.SimulacaoHistoricoEntity;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;
import org.pablofsc.domain.response.SimulacaoInvestimentoResponse;
import org.pablofsc.repository.SimulacaoHistoricoRepository;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@ApplicationScoped
public class SimulacaoInvestimentoService {

  @Inject
  SimulacaoHistoricoRepository historicoRepository;

  @Transactional
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

    ZonedDateTime dataSimulacao = ZonedDateTime.now(ZoneOffset.UTC);

    SimulacaoHistoricoEntity historico = SimulacaoHistoricoEntity.builder()
        .clienteId(request.getClienteId())
        .produto(produtoValidado.getNome())
        .valorInvestido(request.getValor())
        .valorFinal(resultadoSimulacao.getValorFinal())
        .prazoMeses(resultadoSimulacao.getPrazoMeses())
        .dataSimulacao(dataSimulacao)
        .build();

    historicoRepository.persistAndFlush(historico);

    return new SimulacaoInvestimentoResponse(
        produtoValidado,
        resultadoSimulacao,
        dataSimulacao);
  }
}
