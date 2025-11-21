package org.pablofsc.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/**
 * Filtro que captura tempo de início de requisições HTTP.
 * Armazena timestamp para cálculo posterior de tempo de resposta.
 */
@Provider
public class TelemetriaRequestFilter implements ContainerRequestFilter {

  /**
   * Registra o tempo de início da requisição no contexto.
   *
   * @param requestContext Contexto da requisição
   * @throws IOException Erro de I/O
   */
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    // Armazena o tempo de início da requisição
    requestContext.setProperty("tempoInicio", System.currentTimeMillis());
  }
}
