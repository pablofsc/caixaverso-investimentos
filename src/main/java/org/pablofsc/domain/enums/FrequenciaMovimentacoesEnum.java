package org.pablofsc.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FrequenciaMovimentacoesEnum {
  BAIXA("Baixa", 0),
  MEDIA("Média", 1),
  ALTA("Alta", 2);

  private final String descricao;
  private final int nivel;
}
