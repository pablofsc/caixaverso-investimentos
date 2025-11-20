package org.pablofsc.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.response.ProdutoRecomendadoResponse;
import org.pablofsc.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProdutoRecomendadoService {

  @Inject
  ProdutoRepository repository;

  public List<ProdutoRecomendadoResponse> obterProdutosRecomendados(String perfil) {
    List<ProdutoEntity> produtos = repository.listAll(Sort.by("id").ascending());
    return produtos.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
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
