package org.pablofsc.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.InvestimentoEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.UsuarioEntity;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.repository.ClienteRepository;
import org.pablofsc.repository.InvestimentoRepository;
import org.pablofsc.repository.ProdutoRepository;
import org.pablofsc.repository.UsuarioRepository;

import java.time.LocalDate;

@ApplicationScoped
public class DataInitializer {

  @Inject
  ProdutoRepository produtoRepository;

  @Inject
  ClienteRepository clienteRepository;

  @Inject
  InvestimentoRepository investimentoRepository;

  @Inject
  UsuarioRepository usuarioRepository;

  @Transactional
  void onStart(@Observes StartupEvent ev) {
    inicializarUsuarioAdmin();
    inicializarProdutos();
    inicializarClientes();
    inicializarInvestimentos();
  }

  private void inicializarUsuarioAdmin() {
    if (usuarioRepository.count() > 0) {
      return;
    }

    // Criar usuário admin
    usuarioRepository.persist(UsuarioEntity.builder()
        .email("admin@teste.com")
        .senha(BcryptUtil.bcryptHash("123"))
        .nome("Administrador")
        .role("admin")
        .build());
  }

  private void inicializarProdutos() {
    if (produtoRepository.count() > 0) {
      return;
    }

    // CDB (5 produtos)
    produtoRepository.persist(new ProdutoEntity(101L, "CDB Caixa 2026", "CDB", 0.12, NivelRiscoEnum.BAIXO));
    produtoRepository.persist(new ProdutoEntity(102L, "CDB Caixa Top", "CDB", 0.11, NivelRiscoEnum.BAIXO));
    produtoRepository.persist(new ProdutoEntity(103L, "CDB Caixa Maneiro", "CDB", 0.13, NivelRiscoEnum.BAIXO));
    produtoRepository.persist(new ProdutoEntity(104L, "CDB Caixa Seguríssimo", "CDB", 0.10, NivelRiscoEnum.MUITO_BAIXO));
    produtoRepository.persist(new ProdutoEntity(105L, "CDB Caixa Dinheirão", "CDB", 0.17, NivelRiscoEnum.ALTO));

    // Fundo (5 produtos)
    produtoRepository.persist(new ProdutoEntity(106L, "Fundo XPTO", "Fundo", 0.18, NivelRiscoEnum.ALTO));
    produtoRepository.persist(new ProdutoEntity(107L, "Fundo Multimercado Ativo", "Fundo", 0.15, NivelRiscoEnum.ALTO));
    produtoRepository.persist(new ProdutoEntity(108L, "Fundo Balanceado Conservador", "Fundo", 0.09, NivelRiscoEnum.BAIXO));
    produtoRepository.persist(new ProdutoEntity(109L, "Fundo Inovação Tech", "Fundo", 0.25, NivelRiscoEnum.MUITO_ALTO));
    produtoRepository.persist(new ProdutoEntity(110L, "Fundo Renda Variável", "Fundo", 0.20, NivelRiscoEnum.ALTO));

    // Renda Fixa (5 produtos)
    produtoRepository.persist(new ProdutoEntity(111L, "Tesouro Direto IPCA", "Renda Fixa", 0.08, NivelRiscoEnum.MUITO_BAIXO));
    produtoRepository.persist(new ProdutoEntity(112L, "Tesouro Selic Curto", "Renda Fixa", 0.06, NivelRiscoEnum.MUITO_BAIXO));
    produtoRepository.persist(new ProdutoEntity(113L, "Tesouro Prefixado 2030", "Renda Fixa", 0.11, NivelRiscoEnum.MUITO_BAIXO));
    produtoRepository.persist(new ProdutoEntity(119L, "LCI Caixa Imobiliários", "Renda Fixa", 0.10, NivelRiscoEnum.BAIXO));
    produtoRepository.persist(new ProdutoEntity(120L, "LCA Caixa Agrícola", "Renda Fixa", 0.08, NivelRiscoEnum.MUITO_BAIXO));
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
        .riscoMaximoAceitavel(NivelRiscoEnum.MUITO_BAIXO)
        .volumeTotalInvestido(2000.0)
        .frequenciaMovimentacoes("BAIXA")
        .build());

    // Cliente 2: Maria Santos - Moderado, equilíbrio
    clienteRepository.persist(ClienteEntity.builder()
        .id(2L)
        .nome("Maria Santos")
        .prazoMedioPreferido(12)
        .preferenciaRentLiq("EQUILIBRIO")
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(25000.0)
        .frequenciaMovimentacoes("MEDIA")
        .build());

    // Cliente 3: Carlos Oliveira - Agressivo, rentabilidade
    clienteRepository.persist(ClienteEntity.builder()
        .id(3L)
        .nome("Carlos Oliveira")
        .prazoMedioPreferido(24)
        .preferenciaRentLiq("RENTABILIDADE")
        .riscoMaximoAceitavel(NivelRiscoEnum.ALTO)
        .volumeTotalInvestido(100000.0)
        .frequenciaMovimentacoes("ALTA")
        .build());

    // Cliente 4: Ana Costa - Conservadora, liquidez
    clienteRepository.persist(ClienteEntity.builder()
        .id(4L)
        .nome("Ana Costa")
        .prazoMedioPreferido(3)
        .preferenciaRentLiq("LIQUIDEZ")
        .riscoMaximoAceitavel(NivelRiscoEnum.MUITO_BAIXO)
        .volumeTotalInvestido(10000.0)
        .frequenciaMovimentacoes("BAIXA")
        .build());

    // Cliente 5: Pablo Felipe - Investidor ultra agressivo, altíssimo risco
    clienteRepository.persist(ClienteEntity.builder()
        .id(123L)
        .nome("Pablo Felipe")
        .prazoMedioPreferido(48)
        .preferenciaRentLiq("RENTABILIDADE")
        .riscoMaximoAceitavel(NivelRiscoEnum.MUITO_ALTO)
        .volumeTotalInvestido(1000000.0)
        .frequenciaMovimentacoes("ALTA")
        .build());
  }

  private void inicializarInvestimentos() {
    if (investimentoRepository.count() > 0) {
      return;
    }

    ClienteEntity maria = clienteRepository.findById(2L);
    ClienteEntity carlos = clienteRepository.findById(3L);
    ClienteEntity pablo = clienteRepository.findById(123L);

    // Maria (MODERADO) - 5 investimentos
    criarInvestimentos(maria, 5);

    // Carlos (AGRESSIVO) - 10 investimentos
    criarInvestimentos(carlos, 10);

    // Pablo (AGRESSIVO) - 20 investimentos
    criarInvestimentos(pablo, 20);
  }

  private void criarInvestimentos(ClienteEntity cliente, int quantidade) {
    ProdutoEntity[] produtos = new ProdutoEntity[] {
        produtoRepository.findById(101L), // CDB
        produtoRepository.findById(106L), // Fundo
        produtoRepository.findById(111L) // Tesouro
    };

    LocalDate dataBase = LocalDate.parse("2025-01-01");

    for (int i = 0; i < quantidade; i++) {
      ProdutoEntity produtoAleatorio = produtos[i % produtos.length];
      Double valor = 5000.0 + (i * 1000.0); // Valores crescentes
      LocalDate data = dataBase.plusDays(i * 5);

      investimentoRepository.persist(InvestimentoEntity.builder()
          .cliente(cliente)
          .produto(produtoAleatorio)
          .valor(valor)
          .data(data)
          .build());
    }
  }
}
