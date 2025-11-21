package org.pablofsc.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pablofsc.domain.enums.serializer.EnumSerializer;

@Getter
@AllArgsConstructor
@JsonSerialize(using = EnumSerializer.class)
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
