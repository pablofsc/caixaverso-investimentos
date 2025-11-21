package org.pablofsc.resources;

import jakarta.annotation.security.RolesAllowed;
import org.pablofsc.domain.enums.RoleUsuarioEnum;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.pablofsc.domain.exception.ClienteNaoEncontradoException;
import org.pablofsc.domain.exception.ParametroInvalidoException;
import org.pablofsc.domain.exception.ProdutoNaoEncontradoException;
import org.pablofsc.domain.request.SimulacaoInvestimentoRequest;
import org.pablofsc.domain.response.ErrorResponse;
import org.pablofsc.domain.response.SimulacaoInvestimentoResponse;
import org.pablofsc.service.SimulacaoInvestimentoService;

@Path("/simular-investimento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ RoleUsuarioEnum.USER_ROLE, RoleUsuarioEnum.ADMIN_ROLE })
@Tag(name = "Simulação de Investimentos", description = "Endpoints para simular investimentos e análise de produtos")
@SecurityRequirement(name = "bearerAuth")
public class SimulacaoInvestimentoResource {

  @Inject
  SimulacaoInvestimentoService service;

  @POST
  @Operation(summary = "Simular investimento", description = "Realiza uma simulação de investimento baseada nos parâmetros fornecidos, retornando análise do produto validado e resultado da simulação")
  @RequestBody(description = "Parâmetros para simular o investimento", required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SimulacaoInvestimentoRequest.class), examples = @ExampleObject(name = "Simulação válida", value = """
      {
        "clienteId": 456,
        "valor": 75000.00,
        "prazoMeses": 36,
        "tipoProduto": "RENDA_FIXA"
      }""")))
  @APIResponses({
      @APIResponse(responseCode = "200", description = "Simulação realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SimulacaoInvestimentoResponse.class), examples = @ExampleObject(name = "Resultado de simulação", value = """
          {
            "produtoValidado": {
              "id": 12,
              "nome": "Fundo de Renda Fixa Institucional Plus",
              "tipo": "RENDA_FIXA",
              "rentabilidade": 9.8,
              "risco": "BAIXO"
            },
            "resultadoSimulacao": {
              "valorFinal": 84562.50,
              "rentabilidadeEfetiva": 12.75,
              "prazoMeses": 36
            },
            "dataSimulacao": "2025-11-21T15:42:17-03:00"
          }"""))),
      @APIResponse(responseCode = "400", description = "Parâmetros inválidos ou produto não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Erro de parâmetro", value = """
          {
            "mensagem": "Valor de investimento abaixo do mínimo permitido",
            "codigo": "ERR_INSUFFICIENT_VALUE",
            "detalhes": "O valor mínimo para este produto é R$ 5.000,00. Valor informado: R$ 2.500,00",
            "timestamp": "2025-11-21T15:42:17-03:00",
            "path": "/simular-investimento"
          }"""))),
      @APIResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "Cliente não encontrado", value = """
          {
            "mensagem": "Cliente não encontrado",
            "codigo": "ERR_CLIENT_NOT_FOUND",
            "detalhes": "Cliente com ID 999 não existe no sistema. Verifique o ID informado.",
            "timestamp": "2025-11-21T15:42:17-03:00",
            "path": "/simular-investimento"
          }"""))),
      @APIResponse(responseCode = "422", description = "Dados de entrada inválidos", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "401", description = "Não autorizado - Token inválido ou expirado", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
  })
  public Response simularInvestimento(SimulacaoInvestimentoRequest request) {
    try {
      SimulacaoInvestimentoResponse response = service.simularInvestimento(request);
      return Response.ok(response).build();
    } catch (ClienteNaoEncontradoException e) {
      ErrorResponse error = new ErrorResponse(
          "Cliente não encontrado",
          "ERR_CLIENT_NOT_FOUND",
          e.getMessage());
      return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    } catch (ProdutoNaoEncontradoException e) {
      ErrorResponse error = new ErrorResponse(
          "Produto não encontrado",
          "ERR_PRODUCT_NOT_FOUND",
          e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    } catch (ParametroInvalidoException e) {
      ErrorResponse error = new ErrorResponse(
          "Parâmetro inválido",
          "ERR_INVALID_PARAMETER",
          e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
  }
}
