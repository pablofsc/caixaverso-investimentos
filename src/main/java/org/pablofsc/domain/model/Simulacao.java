package org.pablofsc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Simulacao {

  private Double valorFinal;
  private Double rentabilidadeEfetiva;
  private Integer prazoMeses;
}
