package org.pablofsc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pablofsc.domain.entity.TelemetriaEntity;
import org.pablofsc.domain.model.ServicoTelemetria;
import org.pablofsc.domain.response.TelemetriaResponse;
import org.pablofsc.repository.TelemetriaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TelemetriaServiceTest {

  private TelemetriaRepository telemetriaRepository;
  private TelemetriaService telemetriaService;

  @BeforeEach
  void setUp() {
    telemetriaRepository = mock(TelemetriaRepository.class);
    telemetriaService = new TelemetriaService(telemetriaRepository);
  }

  @Test
  void testRegistrarTelemetria() {
    // Arrange
    String endpoint = "/api/simulacao";
    Long tempoRespostaMs = 150L;

    // Act
    telemetriaService.registrarTelemetria(endpoint, tempoRespostaMs);

    // Assert
    verify(telemetriaRepository).persist(any(TelemetriaEntity.class));
  }

  @Test
  void testObterTelemetriasComDados() {
    // Arrange
    LocalDateTime agora = LocalDateTime.now();
    LocalDateTime ontem = agora.minusDays(1);

    TelemetriaEntity telemetria1 = TelemetriaEntity.builder()
        .id(1L)
        .endpoint("/api/simulacao")
        .timestamp(agora)
        .tempoRespostaMs(150L)
        .build();

    TelemetriaEntity telemetria2 = TelemetriaEntity.builder()
        .id(2L)
        .endpoint("/api/simulacao")
        .timestamp(ontem)
        .tempoRespostaMs(200L)
        .build();

    TelemetriaEntity telemetria3 = TelemetriaEntity.builder()
        .id(3L)
        .endpoint("/api/produtos")
        .timestamp(agora)
        .tempoRespostaMs(50L)
        .build();

    when(telemetriaRepository.listAll())
        .thenReturn(Arrays.asList(telemetria1, telemetria2, telemetria3));

    // Act
    TelemetriaResponse response = telemetriaService.obterTelemetrias();

    // Assert
    assertNotNull(response);
    assertNotNull(response.getServicos());
    assertNotNull(response.getPeriodo());

    // Verifica serviços
    assertEquals(2, response.getServicos().size());

    // Serviço /api/simulacao
    ServicoTelemetria servicoSimulacao = response.getServicos().stream()
        .filter(s -> "/api/simulacao".equals(s.getNome()))
        .findFirst()
        .orElse(null);
    assertNotNull(servicoSimulacao);
    assertEquals(2L, servicoSimulacao.getQuantidadeChamadas());
    assertEquals(175L, servicoSimulacao.getMediaTempoRespostaMs()); // (150 + 200) / 2

    // Serviço /api/produtos
    ServicoTelemetria servicoProdutos = response.getServicos().stream()
        .filter(s -> "/api/produtos".equals(s.getNome()))
        .findFirst()
        .orElse(null);
    assertNotNull(servicoProdutos);
    assertEquals(1L, servicoProdutos.getQuantidadeChamadas());
    assertEquals(50L, servicoProdutos.getMediaTempoRespostaMs());

    // Verifica período
    assertEquals(ontem.toLocalDate(), response.getPeriodo().getInicio());
    assertEquals(agora.toLocalDate(), response.getPeriodo().getFim());
  }

  @Test
  void testObterTelemetriasListaVazia() {
    // Arrange
    when(telemetriaRepository.listAll())
        .thenReturn(Collections.emptyList());

    // Act
    TelemetriaResponse response = telemetriaService.obterTelemetrias();

    // Assert
    assertNotNull(response);
    assertNotNull(response.getServicos());
    assertTrue(response.getServicos().isEmpty());
    assertNotNull(response.getPeriodo());

    // Período deve ser hoje quando não há dados
    LocalDate hoje = LocalDate.now();
    assertEquals(hoje, response.getPeriodo().getInicio());
    assertEquals(hoje, response.getPeriodo().getFim());
  }

  @Test
  void testObterTelemetriasApenasUmEndpoint() {
    // Arrange
    LocalDateTime agora = LocalDateTime.now();

    TelemetriaEntity telemetria1 = TelemetriaEntity.builder()
        .id(1L)
        .endpoint("/api/clientes")
        .timestamp(agora)
        .tempoRespostaMs(100L)
        .build();

    TelemetriaEntity telemetria2 = TelemetriaEntity.builder()
        .id(2L)
        .endpoint("/api/clientes")
        .timestamp(agora)
        .tempoRespostaMs(120L)
        .build();

    TelemetriaEntity telemetria3 = TelemetriaEntity.builder()
        .id(3L)
        .endpoint("/api/clientes")
        .timestamp(agora)
        .tempoRespostaMs(80L)
        .build();

    when(telemetriaRepository.listAll())
        .thenReturn(Arrays.asList(telemetria1, telemetria2, telemetria3));

    // Act
    TelemetriaResponse response = telemetriaService.obterTelemetrias();

    // Assert
    assertEquals(1, response.getServicos().size());

    ServicoTelemetria servico = response.getServicos().get(0);
    assertEquals("/api/clientes", servico.getNome());
    assertEquals(3L, servico.getQuantidadeChamadas());
    assertEquals(100L, servico.getMediaTempoRespostaMs()); // (100 + 120 + 80) / 3 = 100

    // Período deve ser apenas hoje
    assertEquals(agora.toLocalDate(), response.getPeriodo().getInicio());
    assertEquals(agora.toLocalDate(), response.getPeriodo().getFim());
  }

  @Test
  void testObterTelemetriasDatasDiferentes() {
    // Arrange
    LocalDateTime ontem = LocalDateTime.now().minusDays(1);
    LocalDateTime anteontem = LocalDateTime.now().minusDays(2);

    TelemetriaEntity telemetria1 = TelemetriaEntity.builder()
        .id(1L)
        .endpoint("/api/teste")
        .timestamp(ontem)
        .tempoRespostaMs(200L)
        .build();

    TelemetriaEntity telemetria2 = TelemetriaEntity.builder()
        .id(2L)
        .endpoint("/api/teste")
        .timestamp(anteontem)
        .tempoRespostaMs(300L)
        .build();

    when(telemetriaRepository.listAll())
        .thenReturn(Arrays.asList(telemetria1, telemetria2));

    // Act
    TelemetriaResponse response = telemetriaService.obterTelemetrias();

    // Assert
    assertEquals(1, response.getServicos().size());

    ServicoTelemetria servico = response.getServicos().get(0);
    assertEquals("/api/teste", servico.getNome());
    assertEquals(2L, servico.getQuantidadeChamadas());
    assertEquals(250L, servico.getMediaTempoRespostaMs()); // (200 + 300) / 2

    // Período deve cobrir desde anteontem até ontem
    assertEquals(anteontem.toLocalDate(), response.getPeriodo().getInicio());
    assertEquals(ontem.toLocalDate(), response.getPeriodo().getFim());
  }

  @Test
  void testCalcularMediaTempoRespostaListaVazia() {
    // Este teste verifica o comportamento interno através do método público
    // Quando não há telemetrias, deve retornar resposta vazia
    when(telemetriaRepository.listAll())
        .thenReturn(Collections.emptyList());

    TelemetriaResponse response = telemetriaService.obterTelemetrias();

    assertTrue(response.getServicos().isEmpty());
  }
}
