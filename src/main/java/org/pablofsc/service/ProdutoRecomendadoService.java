package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.response.ProdutoRecomendadoResponse;

import java.util.List;

@ApplicationScoped
public class ProdutoRecomendadoService {

  public List<ProdutoRecomendadoResponse> obterProdutosRecomendados(String perfil) {
    // Dados mockados
    return List.of(
        new ProdutoRecomendadoResponse(
            101L,
            "CDB Caixa 2026",
            "CDB",
            0.12,
            "Baixo"),
        new ProdutoRecomendadoResponse(
            102L,
            "Fundo XPTO",
            "Fundo",
            0.18,
            "Alto"));
  }
}
