package org.pablofsc.resources;

import jakarta.annotation.security.RolesAllowed;
import org.pablofsc.domain.enums.RoleUsuarioEnum;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pablofsc.domain.response.TelemetriaResponse;
import org.pablofsc.service.TelemetriaService;

@Path("/telemetria")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUsuarioEnum.ADMIN_ROLE)
public class TelemetriaResource {

  @Inject
  TelemetriaService service;

  @GET
  public Response obterTelemetrias() {
    TelemetriaResponse telemetria = service.obterTelemetrias();
    return Response.ok(telemetria).build();
  }
}
