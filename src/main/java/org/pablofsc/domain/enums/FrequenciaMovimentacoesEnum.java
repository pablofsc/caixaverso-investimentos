package org.pablofsc.domain.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pablofsc.domain.enums.serializer.EnumSerializer;

@Getter
@AllArgsConstructor
@JsonSerialize(using = EnumSerializer.class)
public enum FrequenciaMovimentacoesEnum {
  BAIXA("Baixa", 0),
  MEDIA("Média", 1),
  ALTA("Alta", 2);

  private final String descricao;
  private final int nivel;
}
