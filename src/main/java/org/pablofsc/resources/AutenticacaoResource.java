package org.pablofsc.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.pablofsc.domain.request.LoginRequest;
import org.pablofsc.domain.response.LoginResponse;
import org.pablofsc.service.AutenticacaoService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class AutenticacaoResource {

  @Inject
  AutenticacaoService autenticacaoService;

  @POST
  @Path("/login")
  public Response login(LoginRequest request) {
    try {
      LoginResponse response = autenticacaoService.autenticar(request);
      return Response.ok(response).build();
    } catch (Exception e) {
      return Response.status(Response.Status.UNAUTHORIZED)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    }
  }

  @POST
  @Path("/registrar")
  public Response registrar(LoginRequest request) {
    try {
      autenticacaoService.registrar(request);
      return Response.status(Response.Status.CREATED)
          .entity(new MessageResponse("Usuário registrado com sucesso"))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(new ErrorResponse(e.getMessage()))
          .build();
    }
  }

  public record ErrorResponse(String mensagem) {
  }

  public record MessageResponse(String mensagem) {
  }
}
