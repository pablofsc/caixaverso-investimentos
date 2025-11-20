package org.pablofsc.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pablofsc.domain.entity.SimulacaoHistoricoEntity;
import org.pablofsc.domain.response.SimulacaoHistoricoResponse;
import org.pablofsc.repository.SimulacaoHistoricoRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SimulacaoHistoricoService {

  @Inject
  SimulacaoHistoricoRepository repository;

  public List<SimulacaoHistoricoResponse> listarSimulacoes() {
    return repository
        .listAll(Sort.by("id").ascending())
        .stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  private SimulacaoHistoricoResponse toResponse(SimulacaoHistoricoEntity entity) {
    return new SimulacaoHistoricoResponse(
        entity.getId(),
        entity.getClienteId(),
        entity.getProduto(),
        entity.getValorInvestido(),
        entity.getValorFinal(),
        entity.getPrazoMeses(),
        entity.getDataSimulacao());
  }
}
