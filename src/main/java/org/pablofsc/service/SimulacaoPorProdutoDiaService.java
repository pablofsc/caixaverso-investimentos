package org.pablofsc.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.SimulacaoEntity;
import org.pablofsc.domain.model.SimulacaoPorProdutoDia;
import org.pablofsc.repository.SimulacaoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class SimulacaoPorProdutoDiaService {

  private final SimulacaoRepository repository;

  @Inject
  public SimulacaoPorProdutoDiaService(SimulacaoRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public List<SimulacaoPorProdutoDia> listarSimulacoesPorProdutoDia() {
    List<SimulacaoEntity> historicos = repository.listAll(Sort.by("dataSimulacao").descending());

    Map<ProdutoDiaKey, List<SimulacaoEntity>> agrupados = historicos.stream()
      .collect(Collectors.groupingBy(
        this::produtoDiaKey,
        LinkedHashMap::new,
        Collectors.toList()));

    return agrupados.entrySet().stream()
      .map(this::responseFromEntry)
      .sorted(comparatorPorDataEEProduto())
      .collect(Collectors.toCollection(ArrayList::new));
  }

  private SimulacaoPorProdutoDia responseFromEntry(Map.Entry<ProdutoDiaKey, List<SimulacaoEntity>> entry) {
    ProdutoDiaKey key = entry.getKey();
    List<SimulacaoEntity> registros = entry.getValue();
    
    double mediaValorFinal = registros.stream()
        .mapToDouble(SimulacaoEntity::getValorFinal)
        .average()
        .orElse(0.0);

    return new SimulacaoPorProdutoDia(
        key.produto(),
        key.data(),
        registros.size(),
        mediaValorFinal);
  }

  private ProdutoDiaKey produtoDiaKey(SimulacaoEntity entity) {
    String nomeProduto = entity.getProduto() != null ? entity.getProduto().getNome() : "Produto removido";
    return new ProdutoDiaKey(nomeProduto, entity.getDataSimulacao().toLocalDate());
  }

  private Comparator<SimulacaoPorProdutoDia> comparatorPorDataEEProduto() {
    return Comparator.comparing(SimulacaoPorProdutoDia::getData).reversed()
        .thenComparing(SimulacaoPorProdutoDia::getProduto);
  }

  private record ProdutoDiaKey(String produto, LocalDate data) {
  }
}
