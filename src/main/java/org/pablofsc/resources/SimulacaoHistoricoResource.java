package org.pablofsc.resources;

import jakarta.annotation.security.RolesAllowed;
import org.pablofsc.domain.enums.RoleUsuarioEnum;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pablofsc.service.SimulacaoHistoricoService;
import org.pablofsc.service.SimulacaoPorProdutoDiaService;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUsuarioEnum.ADMIN_ROLE)
public class SimulacaoHistoricoResource {

  @Inject
  SimulacaoHistoricoService service;

  @Inject
  SimulacaoPorProdutoDiaService produtoDiaService;

  @GET
  public Response listarSimulacoes() {
    return Response.ok(service.listarSimulacoes()).build();
  }

  @GET
  @Path("/por-produto-dia")
  public Response listarSimulacoesPorProdutoDia() {
    return Response.ok(produtoDiaService.listarSimulacoesPorProdutoDia()).build();
  }
}
