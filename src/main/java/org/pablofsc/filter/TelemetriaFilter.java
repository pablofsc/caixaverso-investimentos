package org.pablofsc.filter;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.pablofsc.service.TelemetriaService;

import java.io.IOException;

@Provider
public class TelemetriaFilter implements ContainerResponseFilter {

  @Inject
  TelemetriaService telemetriaService;

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
      throws IOException {
    // Ignora requisições para /telemetria para evitar loop infinito
    String path = requestContext.getUriInfo().getPath();
    if (path.startsWith("/telemetria")) {
      return;
    }

    // Extrai nome do endpoint sem barras e path params
    String nomeEndpoint = extrairNomeEndpoint(path);

    // Calcula o tempo de resposta em milissegundos
    Long tempoInicio = (Long) requestContext.getProperty("tempoInicio");
    if (tempoInicio == null) {
      // Se não encontrar, calcula 0ms (não deveria acontecer se RequestFilter rodou)
      return;
    }

    Long tempoFim = System.currentTimeMillis();
    Long tempoRespostaMs = tempoFim - tempoInicio;

    // Registra o endpoint e tempo de resposta
    telemetriaService.registrarTelemetria(nomeEndpoint, tempoRespostaMs);
  }

  private String extrairNomeEndpoint(String path) {
    // Remove a primeira barra
    if (path.startsWith("/")) {
      path = path.substring(1);
    }

    // Se for simulacoes/por-produto-dia, deixa inteiro e ignora path params
    if (path.startsWith("simulacoes/por-produto-dia")) {
      return "simulacoes/por-produto-dia";
    }

    // Pega a primeira parte antes de qualquer barra ou número (path param)
    String[] partes = path.split("/");
    return partes[0];
  }
}
