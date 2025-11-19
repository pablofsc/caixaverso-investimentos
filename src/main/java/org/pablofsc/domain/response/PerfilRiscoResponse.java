package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilRiscoResponse {

  private Long clienteId;
  private String perfil;
  private Integer pontuacao;
  private String descricao;
}
