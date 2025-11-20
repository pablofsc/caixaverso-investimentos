package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.SimulacaoHistoricoEntity;
import org.pablofsc.domain.exception.ClienteNaoEncontradoException;
import org.pablofsc.domain.exception.ProdutoNaoEncontradoException;
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

  @Inject
  ValidacaoSimulacaoService validacaoService;

  @Inject
  CalculoSimulacaoService calculoService;

  @Transactional
  public SimulacaoInvestimentoResponse simularInvestimento(SimulacaoInvestimentoRequest request) {
    // Validar parâmetros de entrada (valor, prazo, tipoProduto)
    validacaoService.validar(request);

    // Validar cliente
    ClienteEntity cliente = clienteRepository.findById(request.getClienteId());
    if (cliente == null) {
      throw new ClienteNaoEncontradoException(request.getClienteId());
    }

    // Buscar produto no DB pelo tipo
    ProdutoEntity produtoPersistido = produtoRepository.findByTipo(request.getTipoProduto());
    if (produtoPersistido == null) {
      throw new ProdutoNaoEncontradoException(request.getTipoProduto());
    }

    // Converter para modelo de apresentação
    Produto produtoValidado = new Produto(
        produtoPersistido.getId(),
        produtoPersistido.getNome(),
        produtoPersistido.getTipo(),
        produtoPersistido.getRentabilidade(),
        produtoPersistido.getRisco());

    // Calcular simulação com lógica real
    Double valorFinal = calculoService.calcularValorFinal(
        request.getValor(),
        produtoPersistido.getRentabilidade(),
        request.getPrazoMeses(),
        produtoPersistido.getTipo());

    Simulacao resultadoSimulacao = new Simulacao(
        valorFinal,
        produtoPersistido.getRentabilidade(),
        request.getPrazoMeses());

    ZonedDateTime dataSimulacao = ZonedDateTime.now(ZoneOffset.UTC);

    // Persistir histórico
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
