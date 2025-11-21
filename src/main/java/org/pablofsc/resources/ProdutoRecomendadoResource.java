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
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.service.ProdutoRecomendadoService;

@Path("/produtos-recomendados")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUsuarioEnum.USER_ROLE)
public class ProdutoRecomendadoResource {

  @Inject
  ProdutoRecomendadoService service;

  @GET
  @Path("/{perfil}")
  public Response obterProdutosRecomendados(@PathParam("perfil") String perfil) {
    try {
      PerfilCliente perfilEnum = PerfilCliente.valueOf(perfil.toUpperCase());
      return Response.ok(service.obterProdutosRecomendados(perfilEnum)).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse("Perfil inválido. Use: CONSERVADOR, MODERADO ou AGRESSIVO"))
          .build();
    }
  }

  record ErrorResponse(String mensagem) {
  }
}
