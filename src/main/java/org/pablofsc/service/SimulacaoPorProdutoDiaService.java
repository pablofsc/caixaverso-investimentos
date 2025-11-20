package org.pablofsc.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pablofsc.domain.entity.SimulacaoHistoricoEntity;
import org.pablofsc.domain.response.SimulacaoPorProdutoDiaResponse;
import org.pablofsc.repository.SimulacaoHistoricoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class SimulacaoPorProdutoDiaService {

  @Inject
  SimulacaoHistoricoRepository repository;

  public List<SimulacaoPorProdutoDiaResponse> listarSimulacoesPorProdutoDia() {
    List<SimulacaoHistoricoEntity> historicos = repository.listAll(Sort.by("dataSimulacao").descending());

    Map<ProdutoDiaKey, List<SimulacaoHistoricoEntity>> agrupados = historicos.stream()
      .collect(Collectors.groupingBy(
        this::produtoDiaKey,
        LinkedHashMap::new,
        Collectors.toList()));

    return agrupados.entrySet().stream()
      .map(this::responseFromEntry)
      .sorted(comparatorPorDataEEProduto())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  private SimulacaoPorProdutoDiaResponse responseFromEntry(Map.Entry<ProdutoDiaKey, List<SimulacaoHistoricoEntity>> entry) {
    ProdutoDiaKey key = entry.getKey();
    List<SimulacaoHistoricoEntity> registros = entry.getValue();
    
    double mediaValorFinal = registros.stream()
        .mapToDouble(SimulacaoHistoricoEntity::getValorFinal)
        .average()
        .orElse(0.0);

    return new SimulacaoPorProdutoDiaResponse(
        key.produto(),
        key.data(),
        registros.size(),
        mediaValorFinal);
  }

  private ProdutoDiaKey produtoDiaKey(SimulacaoHistoricoEntity entity) {
    return new ProdutoDiaKey(entity.getProduto(), entity.getDataSimulacao().toLocalDate());
  }

  private Comparator<SimulacaoPorProdutoDiaResponse> comparatorPorDataEEProduto() {
    return Comparator.comparing(SimulacaoPorProdutoDiaResponse::getData).reversed()
        .thenComparing(SimulacaoPorProdutoDiaResponse::getProduto);
  }

  private record ProdutoDiaKey(String produto, LocalDate data) {
  }
}
