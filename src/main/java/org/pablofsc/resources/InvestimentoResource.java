package org.pablofsc.resources;

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
public class InvestimentoResource {

  @Inject
  InvestimentoService service;

  @GET
  @Path("/{clienteId}")
  public Response obterInvestimentos(@PathParam("clienteId") Long clienteId) {
    return Response.ok(service.obterInvestimentos(clienteId)).build();
  }
}
