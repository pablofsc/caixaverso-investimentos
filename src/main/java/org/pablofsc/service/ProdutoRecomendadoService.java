package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.domain.model.ProdutoRecomendado;

import java.util.List;

/**
 * Serviço de recomendação de produtos por perfil de cliente.
 * Utiliza motor de recomendação para buscar produtos compatíveis.
 */
@ApplicationScoped
public class ProdutoRecomendadoService {

  private final MotorRecomendacaoService motorRecomendacao;

  @Inject
  public ProdutoRecomendadoService(MotorRecomendacaoService motorRecomendacao) {
    this.motorRecomendacao = motorRecomendacao;
  }

  /**
   * Retorna produtos recomendados para um perfil de cliente.
   * Utiliza motor de recomendação para buscar e ordenar produtos compatíveis.
   *
   * @param perfil Perfil do cliente (CONSERVADOR, MODERADO ou AGRESSIVO)
   * @return Lista de produtos recomendados com informações resumidas
   */
  public List<ProdutoRecomendado> obterProdutosRecomendados(PerfilCliente perfil) {
    return motorRecomendacao.obterProdutosPorPerfil(perfil).stream()
        .map(this::toResponse)
        .toList();
  }

  /**
   * Converte entidade de produto para modelo de resposta.
   *
   * @param entity Entidade JPA do produto
   * @return Modelo com informações essenciais para apresentação
   */
  private ProdutoRecomendado toResponse(ProdutoEntity entity) {
    return new ProdutoRecomendado(
        entity.getId(),
        entity.getNome(),
        entity.getTipo(),
        entity.getRentabilidade(),
        entity.getRisco());
  }
}
