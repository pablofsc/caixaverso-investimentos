package org.pablofsc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.ClienteEntity;
import org.pablofsc.domain.enums.FrequenciaMovimentacoesEnum;
import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.PreferenciaRentLiqEnum;
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.domain.response.PerfilRiscoResponse;
import org.pablofsc.repository.ClienteRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerfilRiscoServiceTest {

  private ClienteRepository clienteRepository;
  private PerfilRiscoService perfilRiscoService;

  @BeforeEach
  void setUp() {
    clienteRepository = mock(ClienteRepository.class);
    perfilRiscoService = new PerfilRiscoService(clienteRepository);
  }

  @Test
  void testObterPerfilRiscoClienteExistente() {
    // Arrange
    Long clienteId = 1L;
    ClienteEntity cliente = ClienteEntity.builder()
        .id(clienteId)
        .nome("João Silva")
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(5000.0)
        .build();

    when(clienteRepository.findById(clienteId)).thenReturn(cliente);

    // Act
    PerfilRiscoResponse response = perfilRiscoService.obterPerfilRisco(clienteId);

    // Assert
    assertNotNull(response);
    assertEquals(clienteId, response.getClienteId());
    assertEquals("Conservador", response.getPerfil());
    assertEquals(40, response.getPontuacao()); // 10 (freq) + 10 (pref) + 5 (vol) + 15 (risco) = 40
    assertEquals("Foco em segurança e liquidez, movimentação baixa", response.getDescricao());
  }

  @Test
  void testObterPerfilRiscoClienteNaoExistente() {
    // Arrange
    Long clienteId = 999L;
    when(clienteRepository.findById(clienteId)).thenReturn(null);

    // Act
    PerfilRiscoResponse response = perfilRiscoService.obterPerfilRisco(clienteId);

    // Assert
    assertNull(response);
  }

  @Test
  void testCalcularPontuacaoPerfilConservador() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(5000.0)
        .build();

    // Act
    Integer pontuacao = perfilRiscoService.calcularPontuacao(cliente);

    // Assert
    // BAIXA=10, LIQUIDEZ=10, volume 5000=5, BAIXO=15 -> 10+10+5+15=40
    assertEquals(40, pontuacao);
  }

  @Test
  void testCalcularPontuacaoPerfilModerado() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.MEDIA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.EQUILIBRIO)
        .riscoMaximoAceitavel(NivelRiscoEnum.ALTO)
        .volumeTotalInvestido(50000.0)
        .build();

    // Act
    Integer pontuacao = perfilRiscoService.calcularPontuacao(cliente);

    // Assert
    // MEDIA=20, EQUILIBRIO=15, volume 50000=10, ALTO=20 -> 20+15+10+20=65
    assertEquals(65, pontuacao);
  }

  @Test
  void testCalcularPontuacaoPerfilAgressivo() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.ALTA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.RENTABILIDADE)
        .riscoMaximoAceitavel(NivelRiscoEnum.MUITO_ALTO)
        .volumeTotalInvestido(1000000.0)
        .build();

    // Act
    Integer pontuacao = perfilRiscoService.calcularPontuacao(cliente);

    // Assert
    // ALTA=30, RENTABILIDADE=25, volume 1000000=20, MUITO_ALTO=25 ->
    // 30+25+20+25=100
    assertEquals(100, pontuacao);
  }

  @Test
  void testCalcularPontuacaoComValoresNull() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(null)
        .preferenciaRentLiq(null)
        .riscoMaximoAceitavel(null)
        .volumeTotalInvestido(null)
        .build();

    // Act
    Integer pontuacao = perfilRiscoService.calcularPontuacao(cliente);

    // Assert
    assertEquals(0, pontuacao);
  }

  @Test
  void testCalcularPontuacaoVolumeMinimo() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(100.0)
        .build();

    // Act
    Integer pontuacao = perfilRiscoService.calcularPontuacao(cliente);

    // Assert
    // BAIXA=10, LIQUIDEZ=10, volume 100=5, BAIXO=15 -> 10+10+5+15=40
    assertEquals(40, pontuacao);
  }

  @Test
  void testCalcularPontuacaoVolumeMaximo() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.ALTA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.RENTABILIDADE)
        .riscoMaximoAceitavel(NivelRiscoEnum.MUITO_ALTO)
        .volumeTotalInvestido(10000000.0)
        .build();

    // Act
    Integer pontuacao = perfilRiscoService.calcularPontuacao(cliente);

    // Assert
    // ALTA=30, RENTABILIDADE=25, volume 10000000=20, MUITO_ALTO=25 ->
    // 30+25+20+25=100
    assertEquals(100, pontuacao);
  }

  @Test
  void testClassificarPerfilConservador() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.BAIXA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.LIQUIDEZ)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(5000.0)
        .build();

    // Act
    PerfilCliente perfil = perfilRiscoService.classificarPerfil(cliente);

    // Assert
    // Score: freq BAIXA=0, pref LIQUIDEZ=0, vol 5000=0, risco BAIXO=0 -> score=0 ->
    // CONSERVADOR
    assertEquals(PerfilCliente.CONSERVADOR, perfil);
  }

  @Test
  void testClassificarPerfilModerado() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.MEDIA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.EQUILIBRIO)
        .riscoMaximoAceitavel(NivelRiscoEnum.ALTO)
        .volumeTotalInvestido(50000.0)
        .build();

    // Act
    PerfilCliente perfil = perfilRiscoService.classificarPerfil(cliente);

    // Assert
    // Score: freq MEDIA=1, pref EQUILIBRIO=1, vol 50000=1, risco ALTO=1 -> score=4
    // -> MODERADO
    assertEquals(PerfilCliente.MODERADO, perfil);
  }

  @Test
  void testClassificarPerfilAgressivo() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.ALTA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.RENTABILIDADE)
        .riscoMaximoAceitavel(NivelRiscoEnum.MUITO_ALTO)
        .volumeTotalInvestido(1000000.0)
        .build();

    // Act
    PerfilCliente perfil = perfilRiscoService.classificarPerfil(cliente);

    // Assert
    // Score: freq ALTA=2, pref RENTABILIDADE=2, vol 1000000=2, risco MUITO_ALTO=2
    // -> score=8 -> AGRESSIVO
    assertEquals(PerfilCliente.AGRESSIVO, perfil);
  }

  @Test
  void testClassificarPerfilComValoresNull() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(null)
        .preferenciaRentLiq(null)
        .riscoMaximoAceitavel(null)
        .volumeTotalInvestido(null)
        .build();

    // Act
    PerfilCliente perfil = perfilRiscoService.classificarPerfil(cliente);

    // Assert
    assertEquals(PerfilCliente.CONSERVADOR, perfil);
  }

  @Test
  void testClassificarPerfilLimiteConservadorModerado() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.MEDIA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.EQUILIBRIO)
        .riscoMaximoAceitavel(NivelRiscoEnum.BAIXO)
        .volumeTotalInvestido(10000.0)
        .build();

    // Act
    PerfilCliente perfil = perfilRiscoService.classificarPerfil(cliente);

    // Assert
    // Score: freq MEDIA=1, pref EQUILIBRIO=1, vol 10000=1, risco BAIXO=0 -> score=3
    // -> MODERADO (score >=2)
    assertEquals(PerfilCliente.MODERADO, perfil);
  }

  @Test
  void testClassificarPerfilLimiteModeradoAgressivo() {
    // Arrange
    ClienteEntity cliente = ClienteEntity.builder()
        .frequenciaMovimentacoes(FrequenciaMovimentacoesEnum.ALTA)
        .preferenciaRentLiq(PreferenciaRentLiqEnum.RENTABILIDADE)
        .riscoMaximoAceitavel(NivelRiscoEnum.ALTO)
        .volumeTotalInvestido(100000.0)
        .build();

    // Act
    PerfilCliente perfil = perfilRiscoService.classificarPerfil(cliente);

    // Assert
    // Score: freq ALTA=2, pref RENTABILIDADE=2, vol 100000=1.5, risco ALTO=1 ->
    // score=6.5 -> AGRESSIVO (score >=5)
    assertEquals(PerfilCliente.AGRESSIVO, perfil);
  }
}
