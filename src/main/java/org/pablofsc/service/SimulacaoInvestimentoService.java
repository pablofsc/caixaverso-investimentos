package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;
import org.pablofsc.domain.response.SimulacaoInvestimentoResponse;
import org.pablofsc.repository.ClienteRepository;
import org.pablofsc.repository.SimulacaoRepository;
import org.pablofsc.service.helper.SimulacaoOrchestrator;

@ApplicationScoped
public class SimulacaoInvestimentoService {

  private final ValidacaoSimulacaoService validacaoService;
  private final SimulacaoOrchestrator orchestrator;

  @Inject
  public SimulacaoInvestimentoService(
      SimulacaoRepository historicoRepository,
      ClienteRepository clienteRepository,
      ValidacaoSimulacaoService validacaoService,
      CalculoSimulacaoService calculoService,
      MotorRecomendacaoService motorRecomendacao) {
    this.validacaoService = validacaoService;
    this.orchestrator = new SimulacaoOrchestrator(
        clienteRepository,
        historicoRepository,
        motorRecomendacao,
        calculoService);
  }

  // Constructor for testing
  public SimulacaoInvestimentoService(
          ValidacaoSimulacaoService validacaoService,
          SimulacaoOrchestrator orchestrator) {
      this.validacaoService = validacaoService;
      this.orchestrator = orchestrator;
  }

  @Transactional
  public SimulacaoInvestimentoResponse simularInvestimento(SimulacaoInvestimentoRequest request) {
    // Validar parâmetros de entrada (valor, prazo, tipoProduto)
    validacaoService.validar(request);

    // Validar cliente
    var cliente = orchestrator.validarEObterCliente(request.getClienteId());

    // Usar motor de recomendação para buscar o melhor produto para o cliente
    var produtoPersistido = orchestrator.recomendarEValidarProduto(
        cliente,
        request.getTipoProduto(),
        request.getPrazoMeses());

    // Converter para modelo de apresentação
    var produtoValidado = orchestrator.converterProdutoParaModelo(produtoPersistido);

    // Calcular simulação com lógica real
    var resultadoSimulacao = orchestrator.calcularResultado(
        request.getValor(),
        produtoPersistido,
        request.getPrazoMeses());

    var dataSimulacao = orchestrator.obterDataSimulacao();

    // Persistir histórico
    var historico = orchestrator.construirSimulacaoEntity(
        cliente,
        produtoPersistido,
        request.getValor(),
        resultadoSimulacao,
        dataSimulacao);

    orchestrator.persistirSimulacao(historico);

    return new SimulacaoInvestimentoResponse(
        produtoValidado,
        resultadoSimulacao,
        dataSimulacao);
  }
}
