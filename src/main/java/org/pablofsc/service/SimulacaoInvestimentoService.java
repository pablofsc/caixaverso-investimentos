package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.SimulacaoHistoricoEntity;
import org.pablofsc.domain.exception.ClienteNaoEncontradoException;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;
import org.pablofsc.domain.response.SimulacaoInvestimentoResponse;
import org.pablofsc.repository.ClienteRepository;
import org.pablofsc.repository.ProdutoRepository;
import org.pablofsc.repository.SimulacaoHistoricoRepository;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@ApplicationScoped
public class SimulacaoInvestimentoService {

  @Inject
  SimulacaoHistoricoRepository historicoRepository;

  @Inject
  ProdutoRepository produtoRepository;

  @Inject
  ClienteRepository clienteRepository;

  @Transactional
  public SimulacaoInvestimentoResponse simularInvestimento(SimulacaoInvestimentoRequest request) {
      // Validar cliente
      ClienteEntity cliente = clienteRepository.findById(request.getClienteId());
      if (cliente == null) {
          throw new ClienteNaoEncontradoException(request.getClienteId());
      }

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

    ProdutoEntity produtoPersistido = produtoRepository.findById(produtoValidado.getId());
    SimulacaoHistoricoEntity historico = SimulacaoHistoricoEntity.builder()
            .cliente(cliente)
            .produto(produtoPersistido)
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
