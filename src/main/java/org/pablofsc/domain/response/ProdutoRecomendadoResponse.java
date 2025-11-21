package org.pablofsc.domain.response;

import org.pablofsc.domain.enums.NivelRiscoEnum;
import org.pablofsc.domain.enums.TipoProdutoEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRecomendadoResponse {

  private Long id;
  private String nome;
  private TipoProdutoEnum tipo;
  private Double rentabilidade;
  private NivelRiscoEnum risco;
}
