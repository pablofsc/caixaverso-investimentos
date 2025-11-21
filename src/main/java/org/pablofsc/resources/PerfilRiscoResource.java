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
import org.pablofsc.service.PerfilRiscoService;

@Path("/perfil-risco")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUsuarioEnum.ADMIN_ROLE)
public class PerfilRiscoResource {

  @Inject
  PerfilRiscoService service;

  @GET
  @Path("/{clienteId}")
  public Response obterPerfilRisco(@PathParam("clienteId") Long clienteId) {
    return Response.ok(service.obterPerfilRisco(clienteId)).build();
  }
}
