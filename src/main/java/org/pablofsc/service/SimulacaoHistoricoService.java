package org.pablofsc.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.mapper.EntityToModelMapper;
import org.pablofsc.domain.model.SimulacaoHistorico;
import org.pablofsc.repository.SimulacaoRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço de acesso ao histórico de simulações de investimento.
 * Fornece listagem completa de simulações realizadas.
 */
@ApplicationScoped
public class SimulacaoHistoricoService {

  private final SimulacaoRepository repository;

  @Inject
  public SimulacaoHistoricoService(SimulacaoRepository repository) {
    this.repository = repository;
  }

  /**
   * Lista todas as simulações realizadas ordenadas por ID ascendente.
   *
   * @return Lista de históricos de simulação
   */
  @Transactional
  public List<SimulacaoHistorico> listarSimulacoes() {
    return repository
        .listAll(Sort.by("id").ascending())
        .stream()
        .filter(entity -> entity != null)
        .map(EntityToModelMapper::toSimulacaoHistoricoModel)
        .collect(Collectors.toList());
  }
}
