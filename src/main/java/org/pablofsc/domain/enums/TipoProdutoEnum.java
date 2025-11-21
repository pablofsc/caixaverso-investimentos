package org.pablofsc.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoProdutoEnum {
  CDB("CDB"),
  FUNDO("Fundo"),
  RENDA_FIXA("Renda Fixa");

  private final String descricao;
}
