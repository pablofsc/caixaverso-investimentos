package org.pablofsc.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pablofsc.domain.enums.serializer.EnumSerializer;

@Getter
@AllArgsConstructor
@JsonSerialize(using = EnumSerializer.class)
public enum TipoProdutoEnum {
  CDB("CDB"),
  FUNDO("Fundo"),
  RENDA_FIXA("Renda Fixa");

  private final String descricao;
}
