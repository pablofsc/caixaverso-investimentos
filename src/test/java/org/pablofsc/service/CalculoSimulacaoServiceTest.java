package org.pablofsc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.enums.TipoProdutoEnum;

import static org.junit.jupiter.api.Assertions.*;

class CalculoSimulacaoServiceTest {

  private CalculoSimulacaoService calculoSimulacaoService;

  @BeforeEach
  void setUp() {
    calculoSimulacaoService = new CalculoSimulacaoService();
  }

  @Test
  void testCalcularValorFinalComRentabilidadePositiva() {
    // Arrange
    Double valorInicial = 1000.0;
    Double rentabilidade = 0.12; // 12% a.a.
    Integer prazoMeses = 12; // 1 ano
    TipoProdutoEnum tipo = TipoProdutoEnum.CDB;

    // Act
    Double valorFinal = calculoSimulacaoService.calcularValorFinal(valorInicial, rentabilidade, prazoMeses, tipo);

    // Assert
    // Cálculo: 1000 * (1 + 0.12)^1 = 1000 * 1.12 = 1120.00
    assertEquals(1120.00, valorFinal);
  }

  @Test
  void testCalcularValorFinalComRentabilidadeZero() {
    // Arrange
    Double valorInicial = 1000.0;
    Double rentabilidade = 0.0;
    Integer prazoMeses = 12;
    TipoProdutoEnum tipo = TipoProdutoEnum.CDB;

    // Act
    Double valorFinal = calculoSimulacaoService.calcularValorFinal(valorInicial, rentabilidade, prazoMeses, tipo);

    // Assert
    assertEquals(1000.00, valorFinal);
  }

  @Test
  void testCalcularValorFinalComRentabilidadeNegativa() {
    // Arrange
    Double valorInicial = 1000.0;
    Double rentabilidade = -0.05; // -5%
    Integer prazoMeses = 12;
    TipoProdutoEnum tipo = TipoProdutoEnum.CDB;

    // Act
    Double valorFinal = calculoSimulacaoService.calcularValorFinal(valorInicial, rentabilidade, prazoMeses, tipo);

    // Assert
    // Com rentabilidade <= 0, deve retornar o valor inicial
    assertEquals(1000.00, valorFinal);
  }

  @Test
  void testCalcularValorFinalComRentabilidadeNull() {
    // Arrange
    Double valorInicial = 1000.0;
    Double rentabilidade = null;
    Integer prazoMeses = 12;
    TipoProdutoEnum tipo = TipoProdutoEnum.CDB;

    // Act
    Double valorFinal = calculoSimulacaoService.calcularValorFinal(valorInicial, rentabilidade, prazoMeses, tipo);

    // Assert
    assertEquals(1000.00, valorFinal);
  }

  @Test
  void testCalcularValorFinalPrazoParcial() {
    // Arrange
    Double valorInicial = 1000.0;
    Double rentabilidade = 0.10; // 10% a.a.
    Integer prazoMeses = 6; // 6 meses = 0.5 anos
    TipoProdutoEnum tipo = TipoProdutoEnum.CDB;

    // Act
    Double valorFinal = calculoSimulacaoService.calcularValorFinal(valorInicial, rentabilidade, prazoMeses, tipo);

    // Assert
    // Cálculo: 1000 * (1 + 0.10)^0.5 ≈ 1000 * 1.048808848 ≈ 1048.81
    assertEquals(1048.81, valorFinal);
  }

  @Test
  void testCalcularValorFinalPrazoMaior() {
    // Arrange
    Double valorInicial = 1000.0;
    Double rentabilidade = 0.08; // 8% a.a.
    Integer prazoMeses = 24; // 2 anos
    TipoProdutoEnum tipo = TipoProdutoEnum.CDB;

    // Act
    Double valorFinal = calculoSimulacaoService.calcularValorFinal(valorInicial, rentabilidade, prazoMeses, tipo);

    // Assert
    // Cálculo: 1000 * (1 + 0.08)^2 = 1000 * 1.1664 = 1166.40
    assertEquals(1166.40, valorFinal);
  }

  @Test
  void testCalcularValorFinalArredondamento() {
    // Arrange
    Double valorInicial = 1000.0;
    Double rentabilidade = 0.1234; // 12.34% a.a.
    Integer prazoMeses = 12;
    TipoProdutoEnum tipo = TipoProdutoEnum.CDB;

    // Act
    Double valorFinal = calculoSimulacaoService.calcularValorFinal(valorInicial, rentabilidade, prazoMeses, tipo);

    // Assert
    // Deve estar arredondado para 2 casas decimais
    double expected = 1000.0 * Math.pow(1 + 0.1234, 1);
    double roundedExpected = Math.round(expected * 100.0) / 100.0;
    assertEquals(roundedExpected, valorFinal);
  }

  @Test
  void testCalcularValorFinalDiferentesTiposProduto() {
    // Arrange
    Double valorInicial = 1000.0;
    Double rentabilidade = 0.10;
    Integer prazoMeses = 12;

    // Act & Assert - O tipo não afeta o cálculo atualmente
    Double resultadoCDB = calculoSimulacaoService.calcularValorFinal(valorInicial, rentabilidade, prazoMeses,
        TipoProdutoEnum.CDB);
    Double resultadoFundo = calculoSimulacaoService.calcularValorFinal(valorInicial, rentabilidade, prazoMeses,
        TipoProdutoEnum.FUNDO);

    // Assert
    assertEquals(resultadoCDB, resultadoFundo);
    assertEquals(1100.00, resultadoCDB);
  }
}
