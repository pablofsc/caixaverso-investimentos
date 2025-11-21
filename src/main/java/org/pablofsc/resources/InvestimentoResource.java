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
import org.pablofsc.service.InvestimentoService;

@Path("/investimentos")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUsuarioEnum.ADMIN_ROLE)
@Tag(name = "Investimentos", description = "Endpoints para gerenciamento de investimentos de clientes")
@SecurityRequirement(name = "bearerAuth")
public class InvestimentoResource {

  @Inject
  InvestimentoService service;

  @GET
  @Path("/{clienteId}")
  @Operation(summary = "Obter investimentos por cliente", description = "Retorna lista de todos os investimentos de um cliente específico. Requer autenticação e role de ADMIN.")
  @APIResponses({
      @APIResponse(responseCode = "200", description = "Investimentos encontrados", content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = @ExampleObject(name = "Sucesso", value = """
          [
            {
              "id": 1,
              "tipo": "RENDA_FIXA",
              "valor": 25000.00,
              "rentabilidade": 9.8,
              "data": "2025-09-10"
            },
            {
              "id": 2,
              "tipo": "CDB",
              "valor": 15500.50,
              "rentabilidade": 10.2,
              "data": "2025-10-22"
            }
          ]"""))),
      @APIResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Não encontrado", value = """
          {
            "mensagem": "Cliente não encontrado",
            "codigo": "ERR_CLIENT_NOT_FOUND",
            "detalhes": "Cliente com ID 789 não existe. Verifique o identificador informado.",
            "timestamp": "2025-11-21T15:42:17-03:00",
            "path": "/investimentos/789"
          }"""))),
      @APIResponse(responseCode = "401", description = "Não autorizado - Token inválido ou expirado", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "403", description = "Acesso proibido - Permissão insuficiente", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
  })
  public Response obterInvestimentosPorCliente(
      @Parameter(name = "clienteId", description = "Identificador único do cliente", example = "123", required = true) @PathParam("clienteId") Long clienteId) {
    return Response.ok(service.obterInvestimentosPorCliente(clienteId)).build();
  }
}
