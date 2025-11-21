package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ValidationErrorResponse", description = "Resposta de erro de validação com lista de erros por campo")
public class ValidationErrorResponse {

  @Schema(description = "Mensagem geral de erro de validação", minLength = 1)
  private String mensagem;

  @Schema(description = "Código de erro de validação", minLength = 3)
  private String codigo;

  @Schema(description = "Lista de erros de validação agrupados por campo")
  private List<FieldError> erros;

  @Schema(description = "Data e hora do erro no formato ISO-8601")
  private ZonedDateTime timestamp;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Schema(name = "FieldError", description = "Erro de validação de um campo específico")
  public static class FieldError {

    @Schema(description = "Nome do campo que falhou na validação", minLength = 1)
    private String campo;

    @Schema(description = "Mensagem de validação explicando o erro", minLength = 1)
    private String mensagem;

    @Schema(description = "Valor que foi rejeitado pela validação")
    private String valorRejeitado;
  }

  public ValidationErrorResponse(String mensagem, String codigo) {
    this.mensagem = mensagem;
    this.codigo = codigo;
    this.timestamp = ZonedDateTime.now();
  }
}
