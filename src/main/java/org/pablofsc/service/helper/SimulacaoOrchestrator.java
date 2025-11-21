package org.pablofsc.service.helper;

import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.SimulacaoEntity;
import org.pablofsc.domain.exception.ClienteNaoEncontradoException;
import org.pablofsc.domain.exception.ProdutoNaoEncontradoException;
import org.pablofsc.domain.mapper.EntityToModelMapper;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.Simulacao;
import org.pablofsc.repository.ClienteRepository;
import org.pablofsc.repository.SimulacaoRepository;
import org.pablofsc.service.CalculoSimulacaoService;
import org.pablofsc.service.MotorRecomendacaoService;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Orquestrador de simulação - agrupa lógica de validação, recomendação, cálculo
 * e persistência.
 * Facilita testes unitários de cada etapa da simulação.
 */
public class SimulacaoOrchestrator {

  private final ClienteRepository clienteRepository;
  private final SimulacaoRepository historicoRepository;
  private final MotorRecomendacaoService motorRecomendacao;
  private final CalculoSimulacaoService calculoService;

  public SimulacaoOrchestrator(
      ClienteRepository clienteRepository,
      SimulacaoRepository historicoRepository,
      MotorRecomendacaoService motorRecomendacao,
      CalculoSimulacaoService calculoService) {
    this.clienteRepository = clienteRepository;
    this.historicoRepository = historicoRepository;
    this.motorRecomendacao = motorRecomendacao;
    this.calculoService = calculoService;
  }

  /**
   * Valida e obtém cliente do banco de dados
   */
  public ClienteEntity validarEObterCliente(Long clienteId) {
    ClienteEntity cliente = clienteRepository.findById(clienteId);
    if (cliente == null) {
      throw new ClienteNaoEncontradoException(clienteId);
    }
    return cliente;
  }

  /**
   * Recomenda produto e valida se existe
   */
  public ProdutoEntity recomendarEValidarProduto(
      ClienteEntity cliente,
      String tipoProduto,
      Integer prazoMeses) {
    ProdutoEntity produto = motorRecomendacao.recomendarProduto(cliente, tipoProduto, prazoMeses);
    if (produto == null) {
      throw new ProdutoNaoEncontradoException(tipoProduto);
    }
    return produto;
  }

  /**
   * Calcula resultado da simulação (valor final, simulação modelo)
   */
  public Simulacao calcularResultado(
      Double valorInvestido,
      ProdutoEntity produto,
      Integer prazoMeses) {
    Double valorFinal = calculoService.calcularValorFinal(
        valorInvestido,
        produto.getRentabilidade(),
        prazoMeses,
        produto.getTipo());

    return new Simulacao(
        valorFinal,
        produto.getRentabilidade(),
        prazoMeses);
  }

  /**
   * Converte produto para modelo de apresentação
   */
  public Produto converterProdutoParaModelo(ProdutoEntity produto) {
    return EntityToModelMapper.toProdutoModel(produto);
  }

  /**
   * Constrói entidade de simulação para persistência
   */
  public SimulacaoEntity construirSimulacaoEntity(
      ClienteEntity cliente,
      ProdutoEntity produto,
      Double valorInvestido,
      Simulacao resultado,
      ZonedDateTime dataSimulacao) {
    return SimulacaoEntity.builder()
        .cliente(cliente)
        .produto(produto)
        .valorInvestido(valorInvestido)
        .valorFinal(resultado.getValorFinal())
        .prazoMeses(resultado.getPrazoMeses())
        .dataSimulacao(dataSimulacao)
        .build();
  }

  /**
   * Persiste simulação no banco de dados
   */
  public void persistirSimulacao(SimulacaoEntity simulacao) {
    historicoRepository.persistAndFlush(simulacao);
  }

  /**
   * Retorna timestamp UTC atual para simulação
   */
  public ZonedDateTime obterDataSimulacao() {
    return ZonedDateTime.now(ZoneOffset.UTC);
  }
}
