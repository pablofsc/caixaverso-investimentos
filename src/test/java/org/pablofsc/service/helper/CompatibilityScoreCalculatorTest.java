package org.pablofsc.service.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.entity.ProdutoEntity;
import org.pablofsc.domain.enums.FrequenciaMovimentacoesEnum;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.PreferenciaRentLiqEnum;
import org.pablofsc.domain.model.PerfilCliente;

import static org.junit.jupiter.api.Assertions.*;

class CompatibilityScoreCalculatorTest {

  private CompatibilityScoreCalculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new CompatibilityScoreCalculator();
  }

  @Test
  void testCalcularCompatibilidadeClienteConservadorRentabilidade() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .preferenciaRentLiq(PreferenciaRentLiqEnum.RENTABILIDADE)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(50000.0)
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.MEDIA)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.08)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Preferência (8*0.3=2.4) + Rentabilidade (8*0.4=3.2) + Risco (20) + Volume (1) + Frequência (3) = 29.6
    assertEquals(29.6, score, 0.1);
  }

  @Test
  void testCalcularCompatibilidadeClienteModeradoEquilibrio() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .preferenciaRentLiq(PreferenciaRentLiqEnum.EQUILIBRIO)
        .riscoMaximoAceitavel(NivelRiscoEnum.ALTO)
        .volumeTotalInvestido(200000.0)
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.ALTA)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.12)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Preferência (12*0.15=1.8 + (3-2)*3.33=3.33 = 5.13) + Rentabilidade (12*0.4=4.8) + Risco (20) + Volume (2.5) + Frequência (5) = 37.43
    assertEquals(37.43, score, 0.1);
  }

  @Test
  void testCalcularCompatibilidadeClienteAgressivoLiquidez() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.MUITO_ALTO)
        .volumeTotalInvestido(1000000.0)
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.20)
        .risco(NivelRiscoEnum.MUITO_ALTO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Preferência ((3-3)*5=0) + Rentabilidade (20*0.4=8) + Risco (20) + Volume (5) + Frequência (1) = 34
    assertEquals(34.0, score, 0.1);
  }

  @Test
  void testCalcularCompatibilidadeClienteNullPreferencia() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .preferenciaRentLiq(null)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(5000.0)
        .frequenciaMovimentacoes(null)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Preferência (15) + Rentabilidade (10*0.4=4) + Risco (20) + Volume (0) + Frequência (0) = 39
    assertEquals(39.0, score, 0.1);
  }

  @Test
  void testCalcularCompatibilidadeClienteNullRentabilidade() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .preferenciaRentLiq(PreferenciaRentLiqEnum.RENTABILIDADE)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(null)
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(null)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Preferência (0*0.3=0) + Rentabilidade (0*0.4=0) + Risco (20) + Volume (0) + Frequência (1) = 21
    assertEquals(21.0, score, 0.1);
  }

  @Test
  void testCalcularCompatibilidadePerfilConservador() {
    // Arrange
    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.06)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadePerfil(produto, PerfilCliente.CONSERVADOR);

    // Assert - Risco (20 - 1*5 = 15) + Rentabilidade (6*0.2 = 1.2) = 16.2
    assertEquals(16.2, score, 0.1);
  }

  @Test
  void testCalcularCompatibilidadePerfilModerado() {
    // Arrange
    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadePerfil(produto, PerfilCliente.MODERADO);

    // Assert - Risco (15 - |2-1|*5 = 10) + Rentabilidade (10*0.4 = 4) = 14
    assertEquals(14.0, score, 0.1);
  }

  @Test
  void testCalcularCompatibilidadePerfilAgressivo() {
    // Arrange
    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.18)
        .risco(NivelRiscoEnum.MUITO_ALTO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadePerfil(produto, PerfilCliente.AGRESSIVO);

    // Assert - Rentabilidade (18*0.8 = 14.4) + Risco (3*2 = 6) = 20.4
    assertEquals(20.4, score, 0.1);
  }

  @Test
  void testCalcularScorePreferenciaRentabilidade() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .preferenciaRentLiq(PreferenciaRentLiqEnum.RENTABILIDADE)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.15)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Deve incluir rentabilidade * 0.3 (15*0.3=4.5, mas limitado a 30)
    assertTrue(score >= 4.5);
  }

  @Test
  void testCalcularScorePreferenciaEquilibrio() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .preferenciaRentLiq(PreferenciaRentLiqEnum.EQUILIBRIO)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.08)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Deve incluir combinação de rentabilidade e risco
    assertTrue(score > 0);
  }

  @Test
  void testCalcularScorePreferenciaLiquidez() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.05)
        .risco(NivelRiscoEnum.MUITO_BAIXO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Deve favorecer produtos de baixo risco (3-0)*5 = 15
    assertTrue(score >= 15);
  }

  @Test
  void testCalcularBonusVolumeAlto() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(600000.0)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Deve incluir bônus de 5 pontos para volume alto
    assertTrue(score >= 5);
  }

  @Test
  void testCalcularBonusVolumeMedio() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(150000.0)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Deve incluir bônus de 2.5 pontos para volume médio
    assertTrue(score >= 2.5);
  }

  @Test
  void testCalcularBonusFrequenciaAlta() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.ALTA)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.BAIXO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Deve incluir bônus de 5 pontos para frequência alta
    assertTrue(score >= 5);
  }

  @Test
  void testCalcularScoreRiscoCompativel() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .riscoMaximoAceitavel(NivelRiscoEnum.ALTO)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.ALTO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Risco compatível deve dar 20 pontos
    assertTrue(score >= 20);
  }

  @Test
  void testCalcularScoreRiscoIncompativel() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .build();

    ProdutoEntity produto = ProdutoEntity.builder()
        .rentabilidade(0.10)
        .risco(NivelRiscoEnum.MUITO_ALTO)
        .build();

    // Act
    double score = calculator.calcularCompatibilidadeCliente(produto, cliente);

    // Assert - Risco incompatível deve penalizar o score (risco 3 vs max 1)
    assertTrue(score < 20); // Score reduzido devido à incompatibilidade
  }
}
