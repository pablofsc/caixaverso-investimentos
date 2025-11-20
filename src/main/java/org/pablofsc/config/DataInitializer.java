package org.pablofsc.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.repository.ProdutoRepository;

@ApplicationScoped
public class DataInitializer {

  @Inject
  ProdutoRepository produtoRepository;

  @Transactional
  void onStart(@Observes StartupEvent ev) {
    if (produtoRepository.count() > 0) {
      return;
    }

    produtoRepository.persist(new ProdutoEntity(101L, "CDB Caixa 2026", "CDB", 0.12, "Baixo"));
    produtoRepository.persist(new ProdutoEntity(102L, "Fundo XPTO", "Fundo", 0.18, "Alto"));
    produtoRepository.persist(new ProdutoEntity(103L, "Tesouro Direto IPCA", "Renda Fixa", 0.08, "Muito Baixo"));
    produtoRepository.persist(new ProdutoEntity(104L, "Fundo Ações Brasil", "Ações", 0.25, "Muito Alto"));
    produtoRepository.persist(new ProdutoEntity(105L, "LCI Banco do Brasil", "Renda Fixa", 0.10, "Baixo"));
  }
}
