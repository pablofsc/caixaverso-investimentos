package org.pablofsc.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pablofsc.domain.request.LoginRequest;
import org.pablofsc.domain.response.LoginResponse;
import org.pablofsc.domain.response.ValidationErrorResponse;
import org.pablofsc.service.AutenticacaoService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro de usuários")
public class AutenticacaoResource {

  @Inject
  AutenticacaoService autenticacaoService;

  @POST
  @Path("/login")
  @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token JWT válido para 24 horas")
  @APIResponses({
      @APIResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LoginResponse.class), examples = @ExampleObject(name = "Sucesso", value = """
          {
            "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjIwMjQxMTIxIn0.eyJpc3MiOiJjYWl4YXZlcnNvLWludmVzdGltZW50b3MiLCJzdWIiOiI0NTYiLCJlbWFpbCI6ImZlcm5hbmRvLnNpbHZhQGVtYWlsLmNvbS5iciIsImlhdCI6MTczNzQ5MzAwMCwiZXhwIjoxNzM3NTc5NDAwLCJncm91cHMiOlsiVVNFUiJdfQ.ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKU1V6STFOaUlzSW5ZM01tSkNiSE5XWTJ4ellXTjBMbXR2YjNCRGNuUnlJbDA",
            "email": "fernando.silva@email.com.br",
            "nome": "Fernando Silva"
          }"""))),
      @APIResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Credenciais inválidas", value = """
          {
            "mensagem": "Credenciais inválidas",
            "codigo": "ERR_AUTH_401",
            "detalhes": "Email ou senha incorretos. Verifique suas credenciais e tente novamente.",
            "timestamp": "2025-11-21T14:35:22-03:00",
            "path": "/auth/login"
          }"""))),
      @APIResponse(responseCode = "422", description = "Dados de entrada inválidos", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ValidationErrorResponse.class), examples = @ExampleObject(name = "Erro de validação", value = """
          {
            "mensagem": "Erro ao validar requisição",
            "codigo": "ERR_VALIDATION_422",
            "erros": [
              {
                "campo": "email",
                "mensagem": "Email deve conter um endereço válido",
                "valorRejeitado": "usuario_invalido"
              },
              {
                "campo": "senha",
                "mensagem": "Senha deve ter no mínimo 8 caracteres",
                "valorRejeitado": "123456"
              }
            ],
            "timestamp": "2025-11-21T14:35:22-03:00"
          }"""))),
      @APIResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
  })
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
  @Operation(summary = "Registrar novo usuário", description = "Cria uma nova conta de usuário no sistema")
  @APIResponses({
      @APIResponse(responseCode = "201", description = "Usuário registrado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = @ExampleObject(name = "Sucesso", value = """
          {
            "mensagem": "Usuário registrado com sucesso"
          }"""))),
      @APIResponse(responseCode = "400", description = "Email já registrado", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Email duplicado", value = """
          {
            "mensagem": "Email já está registrado",
            "codigo": "ERR_DUPLICATE_EMAIL",
            "detalhes": "O email carlos.mendes@email.com.br já possui uma conta ativa no sistema",
            "timestamp": "2025-11-21T14:35:22-03:00",
            "path": "/auth/registrar"
          }"""))),
      @APIResponse(responseCode = "422", description = "Dados de entrada inválidos", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ValidationErrorResponse.class))),
      @APIResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
  })
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
