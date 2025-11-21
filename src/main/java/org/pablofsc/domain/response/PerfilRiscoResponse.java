package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PerfilRiscoResponse", description = "Resposta contendo análise do perfil de risco do cliente")
public class PerfilRiscoResponse {

  @Schema(description = "ID do cliente", minimum = "1")
  private Long clienteId;

  @Schema(description = "Classificação do perfil (CONSERVADOR, MODERADO, AGRESSIVO)", enumeration = { "CONSERVADOR",
      "MODERADO", "AGRESSIVO" })
  private String perfil;

  @Schema(description = "Pontuação de risco de 0 a 100", minimum = "0", maximum = "100")
  private Integer pontuacao;

  @Schema(description = "Descrição detalhada do perfil de risco", minLength = 10)
  private String descricao;
}
