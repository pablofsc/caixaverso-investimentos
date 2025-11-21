package org.pablofsc.resources;

import jakarta.annotation.security.RolesAllowed;
import org.pablofsc.domain.enums.RoleUsuarioEnum;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pablofsc.service.InvestimentoService;

@Path("/investimentos")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUsuarioEnum.ADMIN_ROLE)
public class InvestimentoResource {

  @Inject
  InvestimentoService service;

  @GET
  @Path("/{clienteId}")
  public Response obterInvestimentosPorCliente(@PathParam("clienteId") Long clienteId) {
    return Response.ok(service.obterInvestimentosPorCliente(clienteId)).build();
  }
}
