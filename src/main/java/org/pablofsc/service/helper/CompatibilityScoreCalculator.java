package org.pablofsc.service.helper;

import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.model.PerfilCliente;

/**
 * Calculadora de scores de compatibilidade entre produtos e clientes/perfis (0-100).
 * Isola lógica de scoring para facilitar testes independentes e manutenção do algoritmo.
 * Considera: rentabilidade, risco, preferência, volume investido e frequência de movimentações.
 */
public class CompatibilityScoreCalculator {

  /**
   * Calcula score de compatibilidade entre produto e cliente (0-100).
   * Componentes: preferência (até 30), rentabilidade (até 40), risco (até 20),
   * bônus volume (até 5) e bônus frequência (até 5).
   *
   * @param produto Produto para avaliar
   * @param cliente Cliente para avaliar compatibilidade
   * @return Score de 0 a 100 onde maior é mais compatível
   */
  public double calcularCompatibilidadeCliente(ProdutoEntity produto, ClienteEntity cliente) {
    double rentabilidade = (produto.getRentabilidade() != null) ? produto.getRentabilidade() * 100 : 0;
    int nivelRisco = produto.getRisco().getNivel();
    int nivelMaximo = cliente.getRiscoMaximoAceitavel().getNivel();

    // Preferência (até 30 pontos)
    double scorePreferencia = calcularScorePreferencia(cliente, rentabilidade, nivelRisco);

    // Rentabilidade (até 40 pontos)
    double scoreRentabilidade = calcularScoreRentabilidade(rentabilidade);

    // Risco (até 20 pontos)
    double scoreRisco = calcularScoreRisco(nivelRisco, nivelMaximo);

    // Bônus de volume (até 5 pontos)
    double bonusVolume = calcularBonusVolume(cliente.getVolumeTotalInvestido());

    // Bônus de frequência (até 5 pontos)
    double bonusFrequencia = calcularBonusFrequencia(cliente.getFrequenciaMovimentacoes());

    return Math.min(scorePreferencia + scoreRentabilidade, 70) + Math.min(scoreRisco, 20) + bonusVolume
        + bonusFrequencia;
  }

  /**
   * Calcula score de compatibilidade entre produto e perfil de cliente.
   * Usa algoritmos diferentes para cada perfil (conservador, moderado, agressivo).
   *
   * @param produto Produto para avaliar
   * @param perfil Perfil do cliente (CONSERVADOR, MODERADO ou AGRESSIVO)
   * @return Score de compatibilidade do produto com o perfil
   */
  public double calcularCompatibilidadePerfil(ProdutoEntity produto, PerfilCliente perfil) {
    double rentabilidade = (produto.getRentabilidade() != null) ? produto.getRentabilidade() * 100 : 0;
    int nivelRisco = produto.getRisco().getNivel();

    return switch (perfil) {
      case CONSERVADOR -> calcularScoreConservador(rentabilidade, nivelRisco);
      case MODERADO -> calcularScoreModerado(rentabilidade, nivelRisco);
      case AGRESSIVO -> calcularScoreAgressivo(rentabilidade, nivelRisco);
    };
  }

  /**
   * Score baseado em preferência de rentabilidade, equilíbrio ou liquidez.
   *
   * @param cliente Cliente para avaliar preferência
   * @param rentabilidade Rentabilidade do produto em percentual
   * @param nivelRisco Nível de risco do produto (1-3)
   * @return Score de preferência (até 30 pontos)
   */
  private double calcularScorePreferencia(ClienteEntity cliente, double rentabilidade, int nivelRisco) {
    return switch (cliente.getPreferenciaRentLiq()) {
      case RENTABILIDADE -> Math.min(rentabilidade * 0.3, 30);
      case EQUILIBRIO -> Math.min(Math.min(rentabilidade * 0.15, 15) + (3 - nivelRisco) * 3.33, 20);
      case LIQUIDEZ -> (3 - nivelRisco) * 5.0;
      case null -> 15.0;
    };
  }

  /**
   * Score baseado em rentabilidade do produto.
   *
   * @param rentabilidade Rentabilidade em percentual
   * @return Score de rentabilidade (até 40 pontos)
   */
  private double calcularScoreRentabilidade(double rentabilidade) {
    return Math.min(rentabilidade * 0.4, 40);
  }

  /**
   * Score baseado em compatibilidade de risco entre cliente e produto.
   *
   * @param nivelRisco Nível de risco do produto
   * @param nivelMaximo Nível máximo de risco aceitável do cliente
   * @return Score de risco (até 20 pontos)
   */
  private double calcularScoreRisco(int nivelRisco, int nivelMaximo) {
    return nivelRisco <= nivelMaximo
        ? 20.0 - (Math.abs(nivelRisco - nivelMaximo) * 5.0)
        : Math.max(0.0, 5.0 - (Math.abs(nivelRisco - nivelMaximo) * 5.0));
  }

  /**
   * Bônus por volume total investido pelo cliente.
   *
   * @param volumeTotalInvestido Volume total investido
   * @return Bônus em pontos (até 5)
   */
  private double calcularBonusVolume(Double volumeTotalInvestido) {
    if (volumeTotalInvestido == null) {
      return 0.0;
    }
    if (volumeTotalInvestido >= 500000)
      return 5.0;
    if (volumeTotalInvestido >= 100000)
      return 2.5;
    if (volumeTotalInvestido >= 10000)
      return 1.0;
    return 0.0;
  }

  /**
   * Bônus por frequência de movimentações do cliente.
   *
   * @param frequencia Frequência (ALTA, MEDIA, BAIXA)
   * @return Bônus em pontos (até 5)
   */
  private double calcularBonusFrequencia(Object frequencia) {
    return switch (frequencia) {
      case Object o when o.toString().equals("ALTA") -> 5.0;
      case Object o when o.toString().equals("MEDIA") -> 3.0;
      case Object o when o.toString().equals("BAIXA") -> 1.0;
      case null -> 0.0;
      default -> 0.0;
    };
  }

  /**
   * Score para perfil conservador - valoriza baixo risco e liquidez.
   *
   * @param rentabilidade Rentabilidade do produto
   * @param nivelRisco Nível de risco do produto
   * @return Score para perfil conservador
   */
  private double calcularScoreConservador(double rentabilidade, int nivelRisco) {
    double riscoScore = Math.max(0, 20 - (nivelRisco * 5));
    return riscoScore + (rentabilidade * 0.2);
  }

  /**
   * Score para perfil moderado - valoriza equilíbrio entre risco e rentabilidade.
   *
   * @param rentabilidade Rentabilidade do produto
   * @param nivelRisco Nível de risco do produto
   * @return Score para perfil moderado
   */
  private double calcularScoreModerado(double rentabilidade, int nivelRisco) {
    double riscoScore = 15 - Math.abs(nivelRisco - 1) * 5;
    return riscoScore + (rentabilidade * 0.4);
  }

  /**
   * Score para perfil agressivo - valoriza alta rentabilidade e maior risco.
   *
   * @param rentabilidade Rentabilidade do produto
   * @param nivelRisco Nível de risco do produto
   * @return Score para perfil agressivo
   */
  private double calcularScoreAgressivo(double rentabilidade, int nivelRisco) {
    return rentabilidade * 0.8 + (nivelRisco * 2);
  }
}
