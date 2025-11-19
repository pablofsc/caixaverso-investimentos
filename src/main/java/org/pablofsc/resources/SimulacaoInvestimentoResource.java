package org.pablofsc.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;
import org.pablofsc.domain.response.SimulacaoInvestimentoResponse;
import org.pablofsc.service.SimulacaoInvestimentoService;

@Path("/simular-investimento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoInvestimentoResource {

  @Inject
  SimulacaoInvestimentoService service;

  @POST
  public Response simularInvestimento(SimulacaoInvestimentoRequest request) {
    SimulacaoInvestimentoResponse response = service.simularInvestimento(request);
    return Response.ok(response).build();
  }
}
