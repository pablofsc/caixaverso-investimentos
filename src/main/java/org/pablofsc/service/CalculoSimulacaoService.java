package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.enums.TipoProdutoEnum;

@ApplicationScoped
public class CalculoSimulacaoService {

  /**
   * Calcula o valor final de uma simulação de investimento.
   *
   * @param valorInicial  Valor inicial investido (R$)
   * @param rentabilidade Taxa de rentabilidade anual (ex: 0.12 = 12% a.a.)
   * @param prazoMeses    Prazo em meses
   * @param tipo          Tipo de produto
   * @return Valor final após aplicar a rentabilidade
   */
  public Double calcularValorFinal(Double valorInicial, Double rentabilidade, Integer prazoMeses,
      TipoProdutoEnum tipo) {
    if (rentabilidade == null || rentabilidade <= 0) {
      return arredondar(valorInicial);
    }

    return calcularJurosCompostos(valorInicial, rentabilidade, prazoMeses);
  }

  /**
   * Juros compostos: M = C * (1 + i)^t
   * Onde: C = capital, i = taxa anual, t = tempo em anos
   */
  private Double calcularJurosCompostos(Double capital, Double taxaAnual, Integer prazoMeses) {
    double tempoEmAnos = prazoMeses / 12.0;
    double resultado = capital * Math.pow(1 + taxaAnual, tempoEmAnos);
    return arredondar(resultado);
  }

  /**
   * Arredonda valor para 2 casas decimais
   */
  private Double arredondar(Double valor) {
    return Math.round(valor * 100.0) / 100.0;
  }
}
