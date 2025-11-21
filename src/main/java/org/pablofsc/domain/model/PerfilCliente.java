package org.pablofsc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PerfilCliente {
  CONSERVADOR("Foco em segurança e liquidez, movimentação baixa"),
  MODERADO("Equilíbrio entre segurança e rentabilidade"),
  AGRESSIVO("Busca por alta rentabilidade, disposição para maior risco");

  private final String descricao;
}
