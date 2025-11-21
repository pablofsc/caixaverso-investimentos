package org.pablofsc.domain.model;

import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRecomendado {

  private Long id;
  private String nome;
  private TipoProdutoEnum tipo;
  private Double rentabilidade;
  private NivelRiscoEnum risco;
}
