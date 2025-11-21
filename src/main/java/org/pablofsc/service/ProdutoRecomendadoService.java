package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.domain.model.ProdutoRecomendado;

import java.util.List;

@ApplicationScoped
public class ProdutoRecomendadoService {

  @Inject
  MotorRecomendacaoService motorRecomendacao;

  /**
   * Retorna produtos recomendados para um perfil, usando o motor de recomendação
   */
  public List<ProdutoRecomendado> obterProdutosRecomendados(PerfilCliente perfil) {
    return motorRecomendacao.obterProdutosPorPerfil(perfil).stream()
        .map(this::toResponse)
        .toList();
  }

  private ProdutoRecomendado toResponse(ProdutoEntity entity) {
    return new ProdutoRecomendado(
        entity.getId(),
        entity.getNome(),
        entity.getTipo(),
        entity.getRentabilidade(),
        entity.getRisco());
  }
}
