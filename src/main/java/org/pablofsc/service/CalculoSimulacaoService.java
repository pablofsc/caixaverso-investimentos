package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.enums.TipoProdutoEnum;

/**
 * Serviço de cálculo de simulação de investimento.
 * Utiliza fórmula de juros compostos para projetar valores finais.
 */
@ApplicationScoped
public class CalculoSimulacaoService {

  /**
   * Calcula valor final usando juros compostos: M = C * (1 + i)^t.
   * C = capital, i = taxa anual, t = tempo em anos.
   *
   * @param valorInicial  Valor inicial investido (R$)
   * @param rentabilidade Taxa de rentabilidade anual (ex: 0.12 = 12% a.a.)
   * @param prazoMeses    Prazo em meses
   * @param tipo          Tipo de produto
   * @return Valor final arredondado a 2 casas decimais
   */
  public Double calcularValorFinal(Double valorInicial, Double rentabilidade, Integer prazoMeses,
      TipoProdutoEnum tipo) {
    if (rentabilidade == null || rentabilidade <= 0) {
      return arredondar(valorInicial);
    }

    return calcularJurosCompostos(valorInicial, rentabilidade, prazoMeses);
  }

  /**
   * Calcula valor com juros compostos: M = C * (1 + i)^t.
   *
   * @param capital Capital inicial
   * @param taxaAnual Taxa de juros anual (ex: 0.12 para 12%)
   * @param prazoMeses Prazo em meses
   * @return Montante final arredondado a 2 casas decimais
   */
  private Double calcularJurosCompostos(Double capital, Double taxaAnual, Integer prazoMeses) {
    double tempoEmAnos = prazoMeses / 12.0;
    double resultado = capital * Math.pow(1 + taxaAnual, tempoEmAnos);
    return arredondar(resultado);
  }

  /**
   * Arredonda valor monetário para 2 casas decimais.
   *
   * @param valor Valor a arredondar
   * @return Valor arredondado
   */
  private Double arredondar(Double valor) {
    return Math.round(valor * 100.0) / 100.0;
  }
}
