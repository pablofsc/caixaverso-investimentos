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
import org.pablofsc.domain.model.PerfilCliente;
import org.pablofsc.domain.response.ErrorResponse;
import org.pablofsc.service.ProdutoRecomendadoService;

@Path("/produtos-recomendados")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({ RoleUsuarioEnum.USER_ROLE, RoleUsuarioEnum.ADMIN_ROLE })
@Tag(name = "Produtos Recomendados", description = "Endpoints para obter recomendações de produtos baseadas no perfil do cliente")
@SecurityRequirement(name = "bearerAuth")
public class ProdutoRecomendadoResource {

  @Inject
  ProdutoRecomendadoService service;

  @GET
  @Path("/{perfil}")
  @Operation(summary = "Obter produtos recomendados por perfil", description = "Retorna lista de produtos de investimento recomendados para um perfil específico de cliente (CONSERVADOR, MODERADO ou AGRESSIVO)")
  @APIResponses({
      @APIResponse(responseCode = "200", description = "Produtos encontrados para o perfil", content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = @ExampleObject(name = "Produtos recomendados", value = """
          [
            {
              "id": 12,
              "nome": "Fundo de Renda Fixa Institucional Plus",
              "tipo": "RENDA_FIXA",
              "rentabilidade": 9.8,
              "risco": "BAIXO"
            },
            {
              "id": 18,
              "nome": "CDB Pós-Fixado Premium",
              "tipo": "CDB",
              "rentabilidade": 10.2,
              "risco": "BAIXO"
            }
          ]"""))),
      @APIResponse(responseCode = "400", description = "Perfil inválido", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Perfil inválido", value = """
          {
            "mensagem": "Perfil inválido. Use: CONSERVADOR, MODERADO ou AGRESSIVO",
            "codigo": "ERR_INVALID_PROFILE",
            "detalhes": "Perfil 'MODERADO_PLUS' não é reconhecido. Perfis válidos: CONSERVADOR, MODERADO, AGRESSIVO",
            "timestamp": "2025-11-21T15:42:17-03:00",
            "path": "/produtos-recomendados/moderado_plus"
          }"""))),
      @APIResponse(responseCode = "401", description = "Não autorizado - Token inválido ou expirado", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
  })
  public Response obterProdutosRecomendados(
      @Parameter(name = "perfil", description = "Perfil de cliente para o qual obter recomendações (CONSERVADOR, MODERADO, AGRESSIVO)", example = "MODERADO", required = true) @PathParam("perfil") String perfil) {
    try {
      PerfilCliente perfilEnum = PerfilCliente.valueOf(perfil.toUpperCase());
      return Response.ok(service.obterProdutosRecomendados(perfilEnum)).build();
    } catch (IllegalArgumentException e) {
      ErrorResponse error = new ErrorResponse(
          "Perfil inválido. Use: CONSERVADOR, MODERADO ou AGRESSIVO",
          "ERR_INVALID_PROFILE",
          "Perfil '" + perfil + "' não é reconhecido");
      return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
  }
}
