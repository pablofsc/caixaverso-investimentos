package org.pablofsc.domain.mapper;

import org.pablofsc.domain.entity.InvestimentoEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.entity.SimulacaoEntity;
import org.pablofsc.domain.model.Investimento;
import org.pablofsc.domain.model.Produto;
import org.pablofsc.domain.model.SimulacaoHistorico;

/**
 * Mapeador para converter entidades JPA para objetos de modelo de domínio.
 */
public class EntityToModelMapper {

  private EntityToModelMapper() {
    // Classe utilitária
  }

  /**
   * Converte InvestimentoEntity para modelo Investimento
   */
  public static Investimento toInvestimentoModel(InvestimentoEntity entity) {
    if (entity == null) {
      return null;
    }

    if (entity.getProduto() == null) {
      return new Investimento(entity.getId(), null, entity.getValor(), null, entity.getData());
    }

    return new Investimento(
        entity.getId(),
        entity.getProduto().getTipo(),
        entity.getValor(),
        entity.getProduto().getRentabilidade(),
        entity.getData());
  }

  /**
   * Converte ProdutoEntity para modelo Produto
   */
  public static Produto toProdutoModel(ProdutoEntity entity) {
    if (entity == null) {
      return null;
    }

    return new Produto(
        entity.getId(),
        entity.getNome(),
        entity.getTipo(),
        entity.getRentabilidade(),
        entity.getRisco());
  }

  /**
   * Converte SimulacaoEntity para modelo SimulacaoHistorico
   */
  public static SimulacaoHistorico toSimulacaoHistoricoModel(SimulacaoEntity entity) {
    if (entity == null) {
      return null;
    }

    return new SimulacaoHistorico(
        entity.getId(),
        entity.getCliente() != null ? entity.getCliente().getId() : null,
        entity.getProduto() != null ? entity.getProduto().getNome() : "Produto removido",
        entity.getValorInvestido(),
        entity.getValorFinal(),
        entity.getPrazoMeses(),
        entity.getDataSimulacao());
  }
}
