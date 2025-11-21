package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.SimulacaoEntity;
import org.pablofsc.domain.exception.ClienteNaoEncontradoException;
import org.pablofsc.domain.exception.ProdutoNaoEncontradoException;
import org.pablofsc.domain.mapper.EntityToModelMapper;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;
import org.pablofsc.domain.response.SimulacaoInvestimentoResponse;
import org.pablofsc.repository.ClienteRepository;
import org.pablofsc.repository.SimulacaoRepository;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@ApplicationScoped
public class SimulacaoInvestimentoService {

  private final SimulacaoRepository historicoRepository;
  private final ClienteRepository clienteRepository;
  private final ValidacaoSimulacaoService validacaoService;
  private final CalculoSimulacaoService calculoService;
  private final MotorRecomendacaoService motorRecomendacao;

  @Inject
  public SimulacaoInvestimentoService(
      SimulacaoRepository historicoRepository,
      ClienteRepository clienteRepository,
      ValidacaoSimulacaoService validacaoService,
      CalculoSimulacaoService calculoService,
      MotorRecomendacaoService motorRecomendacao) {
    this.historicoRepository = historicoRepository;
    this.clienteRepository = clienteRepository;
    this.validacaoService = validacaoService;
    this.calculoService = calculoService;
    this.motorRecomendacao = motorRecomendacao;
  }

  @Transactional
  public SimulacaoInvestimentoResponse simularInvestimento(SimulacaoInvestimentoRequest request) {
    // Validar parâmetros de entrada (valor, prazo, tipoProduto)
    validacaoService.validar(request);

    // Validar cliente
    ClienteEntity cliente = clienteRepository.findById(request.getClienteId());
    if (cliente == null) {
      throw new ClienteNaoEncontradoException(request.getClienteId());
    }

    // Usar motor de recomendação para buscar o melhor produto para o cliente
    ProdutoEntity produtoPersistido = motorRecomendacao.recomendarProduto(
        cliente,
        request.getTipoProduto(),
        request.getPrazoMeses());

    if (produtoPersistido == null) {
      throw new ProdutoNaoEncontradoException(request.getTipoProduto());
    }

    // Converter para modelo de apresentação
    Produto produtoValidado = EntityToModelMapper.toProdutoModel(produtoPersistido);

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
    SimulacaoEntity historico = SimulacaoEntity.builder()
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
