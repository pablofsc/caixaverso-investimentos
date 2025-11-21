package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Resposta de erro padronizada")
public class ErrorResponse {

  @Schema(description = "Mensagem de erro descritiva", minLength = 1)
  private String mensagem;

  @Schema(description = "Código de erro único para identificação programática", minLength = 3)
  private String codigo;

  @Schema(description = "Detalhes adicionais sobre o erro", minLength = 1)
  private String detalhes;

  @Schema(description = "Data e hora do erro no formato ISO-8601")
  private ZonedDateTime timestamp;

  @Schema(description = "Caminho da requisição que gerou o erro")
  private String path;

  public ErrorResponse(String mensagem, String codigo) {
    this.mensagem = mensagem;
    this.codigo = codigo;
    this.timestamp = ZonedDateTime.now();
  }

  public ErrorResponse(String mensagem, String codigo, String detalhes) {
    this.mensagem = mensagem;
    this.codigo = codigo;
    this.detalhes = detalhes;
    this.timestamp = ZonedDateTime.now();
  }
}
