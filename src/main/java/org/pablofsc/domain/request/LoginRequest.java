package org.pablofsc.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginRequest", description = "Requisição para autenticação de usuário")
public class LoginRequest {

  @Schema(description = "Email do usuário (deve ser válido)", minLength = 5, maxLength = 255)
  private String email;

  @Schema(description = "Senha do usuário (mínimo 8 caracteres)", minLength = 8, maxLength = 128)
  private String senha;
}
