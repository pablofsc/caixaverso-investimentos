package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pablofsc.domain.entity.InvestimentoEntity;
import org.pablofsc.domain.model.Investimento;
import org.pablofsc.repository.InvestimentoRepository;

import java.util.List;

@ApplicationScoped
public class InvestimentoService {

  @Inject
  InvestimentoRepository investimentoRepository;

  public List<Investimento> obterInvestimentosPorCliente(Long clienteId) {
    return investimentoRepository.find("cliente.id", clienteId)
        .page(0, Integer.MAX_VALUE)
        .list()
        .stream()
        .sorted((i1, i2) -> i1.getData().compareTo(i2.getData()))
        .map(this::toResponse)
        .toList();
  }

  private Investimento toResponse(InvestimentoEntity investimento) {
    return new Investimento(
        investimento.getId(),
        investimento.getProduto().getTipo(),
        investimento.getValor(),
        investimento.getProduto().getRentabilidade(),
        investimento.getData());
  }
}
