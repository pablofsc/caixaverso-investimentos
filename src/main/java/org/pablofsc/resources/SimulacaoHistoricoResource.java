package org.pablofsc.resources;

import jakarta.annotation.security.RolesAllowed;
import org.pablofsc.domain.enums.RoleUsuarioEnum;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
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
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pablofsc.domain.response.ErrorResponse;
import org.pablofsc.service.SimulacaoHistoricoService;
import org.pablofsc.service.SimulacaoPorProdutoDiaService;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUsuarioEnum.ADMIN_ROLE)
@Tag(name = "Simulações Históricas", description = "Endpoints para consultar histórico de simulações de investimento")
@SecurityRequirement(name = "bearerAuth")
public class SimulacaoHistoricoResource {

  @Inject
  SimulacaoHistoricoService service;

  @Inject
  SimulacaoPorProdutoDiaService produtoDiaService;

  @GET
  @Operation(summary = "Listar todas as simulações", description = "Retorna histórico completo de todas as simulações de investimento realizadas no sistema")
  @APIResponses({
      @APIResponse(responseCode = "200", description = "Simulações listadas com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = @ExampleObject(name = "Lista de simulações", value = """
          [
            {
              "id": 1,
              "clienteId": 456,
              "produto": "Fundo de Renda Fixa Institucional Plus",
              "valorInvestido": 75000.00,
              "valorFinal": 84562.50,
              "prazoMeses": 36,
              "dataSimulacao": "2025-11-20T14:30:00-03:00"
            },
            {
              "id": 2,
              "clienteId": 892,
              "produto": "CDB Pós-Fixado Premium",
              "valorInvestido": 50000.00,
              "valorFinal": 56875.00,
              "prazoMeses": 24,
              "dataSimulacao": "2025-11-21T09:15:00-03:00"
            }
          ]"""))),
      @APIResponse(responseCode = "401", description = "Não autorizado - Token inválido ou expirado", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "403", description = "Acesso proibido - Permissão insuficiente", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
  })
  public Response listarSimulacoes() {
    return Response.ok(service.listarSimulacoes()).build();
  }

  @GET
  @Path("/por-produto-dia")
  @Operation(summary = "Listar simulações por produto e dia", description = "Retorna agregação de simulações agrupadas por produto e dia, útil para análise de tendências")
  @APIResponses({
      @APIResponse(responseCode = "200", description = "Dados agregados retornados com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = @ExampleObject(name = "Simulações agregadas", value = """
          [
            {
              "produto": "Fundo de Renda Fixa Institucional Plus",
              "data": "2025-11-21",
              "quantidadeSimulacoes": 23,
              "mediaValorFinal": 61956.52
            },
            {
              "produto": "CDB Pós-Fixado Premium",
              "data": "2025-11-21",
              "quantidadeSimulacoes": 15,
              "mediaValorFinal": 58333.33
            }
          ]"""))),
      @APIResponse(responseCode = "401", description = "Não autorizado - Token inválido ou expirado", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "403", description = "Acesso proibido - Permissão insuficiente", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
  })
  public Response listarSimulacoesPorProdutoDia() {
    return Response.ok(produtoDiaService.listarSimulacoesPorProdutoDia()).build();
  }
}
