package org.pablofsc.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.response.PerfilRiscoResponse;

@ApplicationScoped
public class PerfilRiscoService {

  public PerfilRiscoResponse obterPerfilRisco(Long clienteId) {
    // Dados mockados
    return new PerfilRiscoResponse(
        clienteId,
        "Moderado",
        65,
        "Perfil equilibrado entre segurança e rentabilidade.");
  }
}
