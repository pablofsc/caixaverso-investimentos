package org.pablofsc.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class TelemetriaRequestFilter implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    // Armazena o tempo de início da requisição
    requestContext.setProperty("tempoInicio", System.currentTimeMillis());
  }
}
