package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pablofsc.domain.mapper.EntityToModelMapper;
import org.pablofsc.domain.model.Investimento;
import org.pablofsc.repository.InvestimentoRepository;

import java.util.List;

@ApplicationScoped
public class InvestimentoService {

  private final InvestimentoRepository investimentoRepository;

  @Inject
  public InvestimentoService(InvestimentoRepository investimentoRepository) {
    this.investimentoRepository = investimentoRepository;
  }

  public List<Investimento> obterInvestimentosPorCliente(Long clienteId) {
    return investimentoRepository.find("cliente.id", clienteId)
        .page(0, Integer.MAX_VALUE)
        .list()
        .stream()
        .sorted((i1, i2) -> i1.getData().compareTo(i2.getData()))
        .map(EntityToModelMapper::toInvestimentoModel)
        .toList();
  }
}
