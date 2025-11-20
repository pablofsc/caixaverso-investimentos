package org.pablofsc.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.response.ProdutoRecomendadoResponse;
import org.pablofsc.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProdutoRecomendadoService {

  @Inject
  ProdutoRepository repository;

  @Transactional
  public List<ProdutoRecomendadoResponse> obterProdutosRecomendados(String perfil) {
    garantirProdutosPadrao();

    List<ProdutoEntity> produtos = repository.listAll(Sort.by("id").ascending());
    return produtos.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  private void garantirProdutosPadrao() {
    if (repository.count() > 0) {
      return;
    }

    repository.persist(new ProdutoEntity(101L, "CDB Caixa 2026", "CDB", 0.12, "Baixo"));
    repository.persist(new ProdutoEntity(102L, "Fundo XPTO", "Fundo", 0.18, "Alto"));
  }

  private ProdutoRecomendadoResponse toResponse(ProdutoEntity entity) {
    return new ProdutoRecomendadoResponse(
        entity.getId(),
        entity.getNome(),
        entity.getTipo(),
        entity.getRentabilidade(),
        entity.getRisco());
  }
}
