package org.pablofsc.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pablofsc.domain.enums.serializer.EnumSerializer;

@Getter
@AllArgsConstructor
@JsonSerialize(using = EnumSerializer.class)
public enum PreferenciaRentLiqEnum {
  LIQUIDEZ("Liquidez"),
  EQUILIBRIO("Equilíbrio"),
  RENTABILIDADE("Rentabilidade");

  private final String descricao;
}
