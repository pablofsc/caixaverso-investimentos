package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.exception.ParametroInvalidoException;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;

@ApplicationScoped
public class ValidacaoSimulacaoService {

  private static final Double VALOR_MINIMO = 100.0;
  private static final Double VALOR_MAXIMO = 1_000_000.0;
  private static final Integer PRAZO_MINIMO = 1;
  private static final Integer PRAZO_MAXIMO = 360; // 30 anos

  public void validar(SimulacaoInvestimentoRequest request) {
    validarValor(request.getValor());
    validarPrazo(request.getPrazoMeses());
    validarTipoProduto(request.getTipoProduto());
  }

  private void validarValor(Double valor) {
    if (valor == null || valor <= 0) {
      throw new ParametroInvalidoException("Valor deve ser maior que zero");
    }
    if (valor < VALOR_MINIMO) {
      throw new ParametroInvalidoException("Valor mínimo é " + VALOR_MINIMO);
    }
    if (valor > VALOR_MAXIMO) {
      throw new ParametroInvalidoException("Valor máximo é " + VALOR_MAXIMO);
    }
  }

  private void validarPrazo(Integer prazo) {
    if (prazo == null || prazo <= 0) {
      throw new ParametroInvalidoException("Prazo deve ser maior que zero");
    }
    if (prazo < PRAZO_MINIMO) {
      throw new ParametroInvalidoException("Prazo mínimo é " + PRAZO_MINIMO + " mês");
    }
    if (prazo > PRAZO_MAXIMO) {
      throw new ParametroInvalidoException("Prazo máximo é " + PRAZO_MAXIMO + " meses");
    }
  }

  private void validarTipoProduto(String tipo) {
    if (tipo == null || tipo.trim().isEmpty()) {
      throw new ParametroInvalidoException("Tipo de produto é obrigatório");
    }
  }
}
