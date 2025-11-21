package org.pablofsc.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pablofsc.domain.enums.serializer.EnumSerializer;

@Getter
@AllArgsConstructor
@JsonSerialize(using = EnumSerializer.class)
public enum PerfilCliente {
  CONSERVADOR("Conservador", "Foco em segurança e liquidez, movimentação baixa"),
  MODERADO("Moderado", "Equilíbrio entre segurança e rentabilidade"),
  AGRESSIVO("Agressivo", "Busca por alta rentabilidade, disposição para maior risco");

  private final String descricao;
  private final String texto;
}
