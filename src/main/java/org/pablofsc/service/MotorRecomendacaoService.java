package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.exception.ProdutoNaoEncontradoException;
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.repository.ProdutoRepository;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class MotorRecomendacaoService {

  @Inject
  ProdutoRepository produtoRepository;

  private static final int NIVEL_MUITO_BAIXO = 0;
  private static final int NIVEL_BAIXO = 1;
  private static final int NIVEL_ALTO = 2;
  private static final int NIVEL_MUITO_ALTO = 3;

  @Transactional
  public ProdutoEntity recomendarProduto(ClienteEntity cliente, String tipoProdutoDesejado, Integer prazoMeses) {
    List<ProdutoEntity> produtos = produtoRepository.find("tipo", tipoProdutoDesejado).list();

    if (produtos.isEmpty()) {
      throw new ProdutoNaoEncontradoException(tipoProdutoDesejado);
    }

    // Filtrar por risco aceitável, caso contrário usar todos
    List<ProdutoEntity> filtrados = produtos.stream()
        .filter(p -> getNivelRisco(p.getRisco()) <= getNivelRisco(cliente.getRiscoMaximoAceitavel()))
        .toList();

    if (filtrados.isEmpty()) {
      filtrados = produtos;
    }

    return filtrados.stream()
        .max(Comparator.comparingDouble(p -> calcularScore(p, cliente)))
        .orElseThrow(() -> new ProdutoNaoEncontradoException(tipoProdutoDesejado));
  }

  private Double calcularScore(ProdutoEntity produto, ClienteEntity cliente) {
    // Classificar perfil do cliente durante o processamento
    classificarPerfil(cliente);

    double rentabilidade = (produto.getRentabilidade() != null) ? produto.getRentabilidade() * 100 : 0;
    int nivelRisco = getNivelRisco(produto.getRisco());
    int nivelMaximo = getNivelRisco(cliente.getRiscoMaximoAceitavel());

    // Preferência (até 30 pontos)
    double scorePreferencia = switch (cliente.getPreferenciaRentLiq()) {
      case "RENTABILIDADE" -> Math.min(rentabilidade * 0.3, 30);
      case "EQUILIBRIO" -> Math.min(Math.min(rentabilidade * 0.15, 15) + (3 - nivelRisco) * 3.33, 20);
      case "LIQUIDEZ" -> (3 - nivelRisco) * 5.0;
      default -> 15.0;
    };

    // Rentabilidade (até 40 pontos)
    double scoreRentabilidade = Math.min(rentabilidade * 0.4, 40);

    // Risco (até 20 pontos)
    double scoreRisco = nivelRisco <= nivelMaximo
        ? 20.0 - (Math.abs(nivelRisco - nivelMaximo) * 5.0)
        : Math.max(0.0, 5.0 - (Math.abs(nivelRisco - nivelMaximo) * 5.0));

    // Bônus de volume (até 5 pontos)
    double bonusVolume = cliente.getVolumeTotalInvestido() != null
        ? cliente.getVolumeTotalInvestido() >= 500000 ? 5.0
            : cliente.getVolumeTotalInvestido() >= 100000 ? 2.5
                : cliente.getVolumeTotalInvestido() >= 10000 ? 1.0 : 0.0
        : 0.0;

    // Bônus de frequência (até 5 pontos)
    double bonusFrequencia = switch (cliente.getFrequenciaMovimentacoes()) {
      case "ALTA" -> 5.0;
      case "MEDIA" -> 3.0;
      case "BAIXA" -> 1.0;
      default -> 0.0;
    };

    return Math.min(scorePreferencia + scoreRentabilidade, 70) + Math.min(scoreRisco, 20) + bonusVolume
        + bonusFrequencia;
  }

  /**
   * Classifica o cliente em uma das três categorias baseado em seu perfil
   * Conservador: baixa movimentação, foco em liquidez
   * Moderado: equilíbrio entre liquidez e rentabilidade
   * Agressivo: busca por alta rentabilidade, maior risco
   */
  public PerfilCliente classificarPerfil(ClienteEntity cliente) {
    int score = 0;

    // Frequência: BAIXA=0, MEDIA=1, ALTA=2
    if ("ALTA".equals(cliente.getFrequenciaMovimentacoes())) {
      score += 2;
    } else if ("MEDIA".equals(cliente.getFrequenciaMovimentacoes())) {
      score += 1;
    }

    // Preferência: LIQUIDEZ=0, EQUILIBRIO=1, RENTABILIDADE=2
    if ("RENTABILIDADE".equals(cliente.getPreferenciaRentLiq())) {
      score += 2;
    } else if ("EQUILIBRIO".equals(cliente.getPreferenciaRentLiq())) {
      score += 1;
    }

    // Volume: 0-10k=0, 10k-100k=1, 100k-500k=1.5, 500k+=2
    if (cliente.getVolumeTotalInvestido() != null) {
      if (cliente.getVolumeTotalInvestido() >= 500000) {
        score += 2;
      } else if (cliente.getVolumeTotalInvestido() >= 100000) {
        score += 1.5;
      } else if (cliente.getVolumeTotalInvestido() >= 10000) {
        score += 1;
      }
    }

    // Risco: Muito Baixo/Baixo=0, Alto=1, Muito Alto=2
    int nivelRisco = getNivelRisco(cliente.getRiscoMaximoAceitavel());
    if (nivelRisco >= NIVEL_MUITO_ALTO) {
      score += 2;
    } else if (nivelRisco >= NIVEL_ALTO) {
      score += 1;
    }

    // Classificação final baseada no score total
    if (score >= 5) {
      return PerfilCliente.AGRESSIVO;
    } else if (score >= 2) {
      return PerfilCliente.MODERADO;
    } else {
      return PerfilCliente.CONSERVADOR;
    }
  }

  private Integer getNivelRisco(String risco) {
    return switch (risco) {
      case "Muito Baixo" -> NIVEL_MUITO_BAIXO;
      case "Baixo" -> NIVEL_BAIXO;
      case "Alto" -> NIVEL_ALTO;
      case "Muito Alto" -> NIVEL_MUITO_ALTO;
      default -> NIVEL_BAIXO;
    };
  }
}
