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

    // CDB (5 produtos)
    produtoRepository.persist(new ProdutoEntity(101L, "CDB Caixa 2026", "CDB", 0.12, "Baixo"));
    produtoRepository.persist(new ProdutoEntity(102L, "CDB Caixa Top", "CDB", 0.11, "Baixo"));
    produtoRepository.persist(new ProdutoEntity(103L, "CDB Caixa Maneiro", "CDB", 0.13, "Baixo"));
    produtoRepository.persist(new ProdutoEntity(104L, "CDB Caixa Seguríssimo", "CDB", 0.10, "Muito Baixo"));
    produtoRepository.persist(new ProdutoEntity(105L, "CDB Caixa Dinheirão", "CDB", 0.17, "Alto"));

    // Fundo (5 produtos)
    produtoRepository.persist(new ProdutoEntity(106L, "Fundo XPTO", "Fundo", 0.18, "Alto"));
    produtoRepository.persist(new ProdutoEntity(107L, "Fundo Multimercado Ativo", "Fundo", 0.15, "Alto"));
    produtoRepository.persist(new ProdutoEntity(108L, "Fundo Balanceado Conservador", "Fundo", 0.09, "Baixo"));
    produtoRepository.persist(new ProdutoEntity(109L, "Fundo Inovação Tech", "Fundo", 0.25, "Muito Alto"));
    produtoRepository.persist(new ProdutoEntity(110L, "Fundo Renda Variável", "Fundo", 0.20, "Alto"));

    // Renda Fixa (5 produtos)
    produtoRepository.persist(new ProdutoEntity(111L, "Tesouro Direto IPCA", "Renda Fixa", 0.08, "Muito Baixo"));
    produtoRepository.persist(new ProdutoEntity(112L, "Tesouro Selic Curto", "Renda Fixa", 0.06, "Muito Baixo"));
    produtoRepository.persist(new ProdutoEntity(113L, "Tesouro Prefixado 2030", "Renda Fixa", 0.11, "Muito Baixo"));
    produtoRepository.persist(new ProdutoEntity(119L, "LCI Caixa Imobiliários", "Renda Fixa", 0.10, "Baixo"));
    produtoRepository.persist(new ProdutoEntity(120L, "LCA Caixa Agrícola", "Renda Fixa", 0.08, "Muito Baixo"));
  }

  private void inicializarClientes() {
    if (clienteRepository.count() > 0) {
      return;
    }

    // Cliente 1: João Silva - Muito conservador, máxima segurança
    clienteRepository.persist(ClienteEntity.builder()
        .id(1L)
        .nome("João Silva")
        .prazoMedioPreferido(3)
        .preferenciaRentLiq("LIQUIDEZ")
        .riscoMaximoAceitavel("Muito Baixo")
        .volumeTotalInvestido(2000.0)
        .frequenciaMovimentacoes("BAIXA")
        .build());

    // Cliente 2: Maria Santos - Moderado, equilíbrio
    clienteRepository.persist(ClienteEntity.builder()
        .id(2L)
        .nome("Maria Santos")
        .prazoMedioPreferido(12)
        .preferenciaRentLiq("EQUILIBRIO")
        .riscoMaximoAceitavel("Baixo")
        .volumeTotalInvestido(25000.0)
        .frequenciaMovimentacoes("MEDIA")
        .build());

    // Cliente 3: Carlos Oliveira - Agressivo, rentabilidade
    clienteRepository.persist(ClienteEntity.builder()
        .id(3L)
        .nome("Carlos Oliveira")
        .prazoMedioPreferido(24)
        .preferenciaRentLiq("RENTABILIDADE")
        .riscoMaximoAceitavel("Alto")
        .volumeTotalInvestido(100000.0)
        .frequenciaMovimentacoes("ALTA")
        .build());

    // Cliente 4: Ana Costa - Conservadora, liquidez
    clienteRepository.persist(ClienteEntity.builder()
        .id(4L)
        .nome("Ana Costa")
        .prazoMedioPreferido(3)
        .preferenciaRentLiq("LIQUIDEZ")
        .riscoMaximoAceitavel("Muito Baixo")
        .volumeTotalInvestido(10000.0)
        .frequenciaMovimentacoes("BAIXA")
        .build());

    // Cliente 5: Pablo Felipe - Investidor ultra agressivo, altíssimo risco
    clienteRepository.persist(ClienteEntity.builder()
        .id(123L)
        .nome("Pablo Felipe")
        .prazoMedioPreferido(48)
        .preferenciaRentLiq("RENTABILIDADE")
        .riscoMaximoAceitavel("Muito Alto")
        .volumeTotalInvestido(1000000.0)
        .frequenciaMovimentacoes("ALTA")
        .build());
  }
}
