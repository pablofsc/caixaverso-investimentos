package org.pablofsc.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pablofsc.service.ProdutoRecomendadoService;

@Path("/produtos-recomendados")
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoRecomendadoResource {

  @Inject
  ProdutoRecomendadoService service;

  @GET
  @Path("/{perfil}")
  public Response obterProdutosRecomendados(@PathParam("perfil") String perfil) {
    return Response.ok(service.obterProdutosRecomendados(perfil)).build();
  }
}
