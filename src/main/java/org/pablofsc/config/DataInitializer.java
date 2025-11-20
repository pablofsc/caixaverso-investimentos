package org.pablofsc.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.repository.ClienteRepository;
import org.pablofsc.repository.ProdutoRepository;

@ApplicationScoped
public class DataInitializer {

  @Inject
  ProdutoRepository produtoRepository;

  @Inject
  ClienteRepository clienteRepository;

  @Transactional
  void onStart(@Observes StartupEvent ev) {
    inicializarProdutos();
    inicializarClientes();
  }

  private void inicializarProdutos() {
    if (produtoRepository.count() > 0) {
      return;
    }

    produtoRepository.persist(new ProdutoEntity(101L, "CDB Caixa 2026", "CDB", 0.12, "Baixo"));
    produtoRepository.persist(new ProdutoEntity(102L, "Fundo XPTO", "Fundo", 0.18, "Alto"));
    produtoRepository.persist(new ProdutoEntity(103L, "Tesouro Direto IPCA", "Renda Fixa", 0.08, "Muito Baixo"));
    produtoRepository.persist(new ProdutoEntity(104L, "Fundo RF", "Renda Fixa", 0.25, "Muito Alto"));
    produtoRepository.persist(new ProdutoEntity(105L, "LCI Banco do Brasil", "Renda Fixa", 0.10, "Baixo"));
  }

  private void inicializarClientes() {
    if (clienteRepository.count() > 0) {
      return;
    }

    clienteRepository.persist(new ClienteEntity(1L, "João Silva"));
    clienteRepository.persist(new ClienteEntity(2L, "Maria Santos"));
    clienteRepository.persist(new ClienteEntity(3L, "Carlos Oliveira"));
    clienteRepository.persist(new ClienteEntity(4L, "Ana Costa"));
    clienteRepository.persist(new ClienteEntity(123L, "Pablo Felipe"));
  }
}
