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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pablofsc.domain.response.ErrorResponse;
import org.pablofsc.domain.response.PerfilRiscoResponse;
import org.pablofsc.service.PerfilRiscoService;

@Path("/perfil-risco")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUsuarioEnum.ADMIN_ROLE)
@Tag(name = "Perfil de Risco", description = "Endpoints para análise e consulta do perfil de risco de clientes")
@SecurityRequirement(name = "bearerAuth")
public class PerfilRiscoResource {

  @Inject
  PerfilRiscoService service;

  @GET
  @Path("/{clienteId}")
  @Operation(summary = "Obter perfil de risco do cliente", description = "Retorna a análise completa do perfil de risco de um cliente, incluindo classificação e recomendações de investimento")
  @APIResponses({
      @APIResponse(responseCode = "200", description = "Perfil de risco encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PerfilRiscoResponse.class), examples = @ExampleObject(name = "Perfil de risco", value = """
          {
            "clienteId": 456,
            "perfil": "MODERADO",
            "pontuacao": 62,
            "descricao": "Equilíbrio entre segurança e rentabilidade. Investidor com tolerância moderada ao risco, adequado para portfólios diversificados com 50% renda fixa e 50% ações."
          }"""))),
      @APIResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Não encontrado", value = """
          {
            "mensagem": "Cliente não encontrado",
            "codigo": "ERR_CLIENT_NOT_FOUND",
            "detalhes": "Cliente com ID 999 não possui perfil de risco definido. Realize avaliação completa primeiro.",
            "timestamp": "2025-11-21T15:42:17-03:00",
            "path": "/perfil-risco/999"
          }"""))),
      @APIResponse(responseCode = "401", description = "Não autorizado - Token inválido ou expirado", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "403", description = "Acesso proibido - Permissão insuficiente", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
  })
  public Response obterPerfilRisco(
      @Parameter(name = "clienteId", description = "Identificador único do cliente", example = "123", required = true) @PathParam("clienteId") Long clienteId) {
    return Response.ok(service.obterPerfilRisco(clienteId)).build();
  }
}
