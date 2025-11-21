package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pablofsc.domain.entity.TelemetriaEntity;
import org.pablofsc.domain.model.Periodo;
import org.pablofsc.domain.model.ServicoTelemetria;
import org.pablofsc.domain.response.TelemetriaResponse;
import org.pablofsc.repository.TelemetriaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço de telemetria para monitoramento de requisições HTTP.
 * Registra tempo de resposta por endpoint e fornece relatórios agregados.
 */
@ApplicationScoped
public class TelemetriaService {

  private final TelemetriaRepository telemetriaRepository;

  @Inject
  public TelemetriaService(TelemetriaRepository telemetriaRepository) {
    this.telemetriaRepository = telemetriaRepository;
  }

  /**
   * Registra métrica de telemetria de uma requisição.
   *
   * @param endpoint Caminho do endpoint (ex: /api/simulacao)
   * @param tempoRespostaMs Tempo de resposta em milissegundos
   */
  @Transactional
  public void registrarTelemetria(String endpoint, Long tempoRespostaMs) {
    TelemetriaEntity telemetria = TelemetriaEntity.builder()
        .endpoint(endpoint)
        .timestamp(LocalDateTime.now())
        .tempoRespostaMs(tempoRespostaMs)
        .build();

    telemetriaRepository.persist(telemetria);
  }

  /**
   * Obtém relatório completo de telemetria agregado por endpoint.
   * Inclui contagem de requisições, tempo médio de resposta e período.
   *
   * @return Resposta contendo lista de serviços com métricas e período de coleta
   */
  public TelemetriaResponse obterTelemetrias() {
    List<TelemetriaEntity> todasAsTelemetrias = telemetriaRepository.listAll();

    // Agrupa por endpoint
    Map<String, List<TelemetriaEntity>> agrupadosPorEndpoint = todasAsTelemetrias.stream()
        .collect(Collectors.groupingBy(TelemetriaEntity::getEndpoint));

    // Cria lista de ServicoTelemetria com agregações
    List<ServicoTelemetria> servicos = agrupadosPorEndpoint.entrySet().stream()
        .map(this::criarServicoTelemetria)
        .toList();

    // Calcula período de início (primeiro registro) até fim (último registro)
    LocalDate dataInicio = LocalDate.now();
    LocalDate dataFim = LocalDate.now();

    if (!todasAsTelemetrias.isEmpty()) {
      dataInicio = todasAsTelemetrias.stream()
          .map(t -> t.getTimestamp().toLocalDate())
          .min(LocalDate::compareTo)
          .orElse(LocalDate.now());

      dataFim = todasAsTelemetrias.stream()
          .map(t -> t.getTimestamp().toLocalDate())
          .max(LocalDate::compareTo)
          .orElse(LocalDate.now());
    }

    Periodo periodo = new Periodo(dataInicio, dataFim);

    return new TelemetriaResponse(servicos, periodo);
  }

  /**
   * Cria objeto de serviço de telemetria com agregações de um endpoint.
   *
   * @param entry Entrada mapa com endpoint e lista de telemetrias
   * @return Objeto ServicoTelemetria com contagem e tempo médio calculados
   */
  private ServicoTelemetria criarServicoTelemetria(Map.Entry<String, List<TelemetriaEntity>> entry) {
    return new ServicoTelemetria(
        entry.getKey(),
        (long) entry.getValue().size(),
        calcularMediaTempoResposta(entry.getValue()));
  }

  /**
   * Calcula tempo médio de resposta para lista de telemetrias.
   *
   * @param telemetrias Lista de registros de telemetria
   * @return Tempo médio em milissegundos
   */
  private Long calcularMediaTempoResposta(List<TelemetriaEntity> telemetrias) {
    if (telemetrias.isEmpty()) {
      return 0L;
    }

    long soma = telemetrias.stream()
        .mapToLong(TelemetriaEntity::getTempoRespostaMs)
        .sum();

    return soma / telemetrias.size();
  }
}
