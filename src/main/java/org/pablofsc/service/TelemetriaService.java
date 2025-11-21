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

@ApplicationScoped
public class TelemetriaService {

  @Inject
  TelemetriaRepository telemetriaRepository;

  @Transactional
  public void registrarTelemetria(String endpoint, Long tempoRespostaMs) {
    TelemetriaEntity telemetria = TelemetriaEntity.builder()
        .endpoint(endpoint)
        .timestamp(LocalDateTime.now())
        .tempoRespostaMs(tempoRespostaMs)
        .build();

    telemetriaRepository.persist(telemetria);
  }

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

  private ServicoTelemetria criarServicoTelemetria(Map.Entry<String, List<TelemetriaEntity>> entry) {
    return new ServicoTelemetria(
        entry.getKey(),
        (long) entry.getValue().size(),
        calcularMediaTempoResposta(entry.getValue()));
  }

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
