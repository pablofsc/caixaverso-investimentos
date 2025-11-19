package org.pablofsc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

  private Long id;
  private String nome;
  private String tipo;
  private Double rentabilidade;
  private String risco;
}
