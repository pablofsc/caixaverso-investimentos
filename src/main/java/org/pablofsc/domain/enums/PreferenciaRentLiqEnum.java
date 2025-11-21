package org.pablofsc.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PreferenciaRentLiqEnum {
  LIQUIDEZ("LIQUIDEZ"),
  EQUILIBRIO("EQUILIBRIO"),
  RENTABILIDADE("RENTABILIDADE");

  private final String descricao;
}
