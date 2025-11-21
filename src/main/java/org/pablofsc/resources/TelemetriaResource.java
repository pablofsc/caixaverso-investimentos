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
import org.pablofsc.domain.response.TelemetriaResponse;
import org.pablofsc.service.TelemetriaService;

@Path("/telemetria")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(RoleUsuarioEnum.ADMIN_ROLE)
@Tag(name = "Telemetria", description = "Endpoints para monitoramento e coleta de dados operacionais do sistema")
@SecurityRequirement(name = "bearerAuth")
public class TelemetriaResource {

  @Inject
  TelemetriaService service;

  @GET
  @Operation(summary = "Obter telemetrias do sistema", description = "Retorna métricas operacionais e dados de monitoramento do sistema, incluindo performance, acessos e erros")
  @APIResponses({
      @APIResponse(responseCode = "200", description = "Telemetrias obtidas com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TelemetriaResponse.class), examples = @ExampleObject(name = "Dados de telemetria", value = """
          {
            "servicos": [
              {
                "nome": "API de Autenticação",
                "quantidadeChamadas": 12543,
                "mediaTempoRespostaMs": 145
              },
              {
                "nome": "API de Simulação",
                "quantidadeChamadas": 8234,
                "mediaTempoRespostaMs": 287
              },
              {
                "nome": "API de Investimentos",
                "quantidadeChamadas": 5421,
                "mediaTempoRespostaMs": 198
              }
            ],
            "periodo": {
              "inicio": "2025-11-21",
              "fim": "2025-11-21"
            }
          }"""))),
      @APIResponse(responseCode = "401", description = "Não autorizado - Token inválido ou expirado", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "403", description = "Acesso proibido - Permissão insuficiente", content = @Content(mediaType = MediaType.APPLICATION_JSON)),
      @APIResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class)))
  })
  public Response obterTelemetrias() {
    TelemetriaResponse telemetria = service.obterTelemetrias();
    return Response.ok(telemetria).build();
  }
}
