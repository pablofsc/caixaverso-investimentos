package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "SuccessResponse", description = "Resposta genérica de sucesso com dados (genérica)")
public class SuccessResponse<T> {

  @Schema(description = "Mensagem de sucesso", minLength = 1)
  private String mensagem;

  @Schema(description = "Dados da resposta (genérico - tipo depende do contexto)")
  private T data;

  @Schema(description = "Data e hora da resposta no formato ISO-8601")
  private ZonedDateTime timestamp;

  public SuccessResponse(String mensagem, T data) {
    this.mensagem = mensagem;
    this.data = data;
    this.timestamp = ZonedDateTime.now();
  }
}
