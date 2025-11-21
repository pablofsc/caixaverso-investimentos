package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pablofsc.domain.enums.TipoProdutoEnum;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestimentoResponse {

  private Long id;
  private TipoProdutoEnum tipo;
  private Double valor;
  private Double rentabilidade;
  private LocalDate data;
}
