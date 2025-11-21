package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.exception.ProdutoNaoEncontradoException;
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.repository.ProdutoRepository;
import org.pablofsc.service.helper.CompatibilityScoreCalculator;

import java.util.List;

@ApplicationScoped
public class MotorRecomendacaoService {

  private final ProdutoRepository produtoRepository;
  private final PerfilRiscoService perfilRiscoService;
  private final CompatibilityScoreCalculator scoreCalculator;

  @Inject
  public MotorRecomendacaoService(ProdutoRepository produtoRepository, PerfilRiscoService perfilRiscoService) {
    this.produtoRepository = produtoRepository;
    this.perfilRiscoService = perfilRiscoService;
    this.scoreCalculator = new CompatibilityScoreCalculator();
  }

  /**
   * Retorna produtos compatíveis com cliente, ordenados por compatibilidade
   */
  @Transactional
  public List<ProdutoEntity> obterProdutosCompativeis(ClienteEntity cliente, String tipoProdutoDesejado) {
    // Primeiro filtrar por perfil do cliente
    PerfilCliente perfil = perfilRiscoService.classificarPerfil(cliente);
    List<ProdutoEntity> produtosPorPerfil = obterProdutosPorPerfil(perfil);

    // Depois filtrar pelo tipo desejado
    List<ProdutoEntity> produtos = produtosPorPerfil.stream()
        .filter(p -> p.getTipo().getDescricao().equals(tipoProdutoDesejado))
        .toList();

    if (produtos.isEmpty()) {
      throw new ProdutoNaoEncontradoException(tipoProdutoDesejado);
    }

    // Filtrar por risco aceitável do cliente
    List<ProdutoEntity> filtrados = produtos.stream()
        .filter(p -> p.getRisco().getNivel() <= cliente.getRiscoMaximoAceitavel().getNivel())
        .toList();

    if (filtrados.isEmpty()) {
      filtrados = produtos;
    }

    // Ordenar por compatibilidade com cliente
    return filtrados.stream()
        .sorted((p1, p2) -> Double.compare(
            scoreCalculator.calcularCompatibilidadeCliente(p2, cliente),
            scoreCalculator.calcularCompatibilidadeCliente(p1, cliente)))
        .toList();
  }

  /**
   * Retorna produtos recomendados para um perfil, ordenados por compatibilidade
   */
  public List<ProdutoEntity> obterProdutosPorPerfil(PerfilCliente perfil) {
    return produtoRepository.listAll().stream()
        .filter(p -> filtrarPorPerfil(p, perfil))
        .sorted((p1, p2) -> Double.compare(
            scoreCalculator.calcularCompatibilidadePerfil(p2, perfil),
            scoreCalculator.calcularCompatibilidadePerfil(p1, perfil)))
        .toList();
  }

  /**
   * Retorna o produto mais compatível com cliente
   */
  public ProdutoEntity recomendarProduto(ClienteEntity cliente, String tipoProdutoDesejado, Integer prazoMeses) {
    List<ProdutoEntity> compatíveis = obterProdutosCompativeis(cliente, tipoProdutoDesejado);
    return compatíveis.get(0); // Primeiro é o mais compatível
  }

  private boolean filtrarPorPerfil(ProdutoEntity produto, PerfilCliente perfil) {
    int nivelRisco = produto.getRisco().getNivel();

    return switch (perfil) {
      case CONSERVADOR -> nivelRisco <= NivelRiscoEnum.BAIXO.getNivel();
      case MODERADO -> nivelRisco <= NivelRiscoEnum.ALTO.getNivel();
      case AGRESSIVO -> true;
    };
  }

}
