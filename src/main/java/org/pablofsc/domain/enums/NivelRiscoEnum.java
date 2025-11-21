package org.pablofsc.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NivelRiscoEnum {
  MUITO_BAIXO("Muito Baixo", 0),
  BAIXO("Baixo", 1),
  ALTO("Alto", 2),
  MUITO_ALTO("Muito Alto", 3);

  private final String descricao;
  private final int nivel;

  public static NivelRiscoEnum fromDescricao(String descricao) {
    if (descricao == null) {
      return BAIXO;
    }
    return switch (descricao) {
      case "Muito Baixo" -> MUITO_BAIXO;
      case "Baixo" -> BAIXO;
      case "Alto" -> ALTO;
      case "Muito Alto" -> MUITO_ALTO;
      default -> BAIXO;
    };
  }
}
