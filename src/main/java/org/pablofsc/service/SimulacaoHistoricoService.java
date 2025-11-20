package org.pablofsc.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.SimulacaoHistoricoEntity;
import org.pablofsc.domain.response.SimulacaoHistoricoResponse;
import org.pablofsc.repository.SimulacaoHistoricoRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SimulacaoHistoricoService {

  @Inject
  SimulacaoHistoricoRepository repository;

  @Transactional
  public List<SimulacaoHistoricoResponse> listarSimulacoes() {
    return repository
        .listAll(Sort.by("id").ascending())
        .stream()
        .filter(entity -> entity != null)
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  private SimulacaoHistoricoResponse toResponse(SimulacaoHistoricoEntity entity) {
    if (entity == null) {
      return null;
    }

    return new SimulacaoHistoricoResponse(
        entity.getId(),
        entity.getCliente() != null ? entity.getCliente().getId() : null,
        entity.getProduto() != null ? entity.getProduto().getNome() : "Produto removido",
        entity.getValorInvestido(),
        entity.getValorFinal(),
        entity.getPrazoMeses(),
        entity.getDataSimulacao());
  }
}
