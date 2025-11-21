package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginResponse", description = "Resposta de autenticação com token JWT")
public class LoginResponse {

  @Schema(description = "Token JWT para autenticação em requisições subsequentes", minLength = 10)
  private String token;

  @Schema(description = "Email do usuário autenticado", format = "email")
  private String email;

  @Schema(description = "Nome completo do usuário", minLength = 1, maxLength = 255)
  private String nome;
}
