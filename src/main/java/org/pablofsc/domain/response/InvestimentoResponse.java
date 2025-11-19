package org.pablofsc.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestimentoResponse {

  private Long id;
  private String tipo;
  private Double valor;
  private Double rentabilidade;
  private LocalDate data;
}
