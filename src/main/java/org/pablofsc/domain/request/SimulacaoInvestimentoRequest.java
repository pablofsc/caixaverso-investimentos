package org.pablofsc.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SimulacaoInvestimentoRequest", description = "Requisição para simular um investimento")
public class SimulacaoInvestimentoRequest {

  @Schema(description = "ID do cliente que fará o investimento", minimum = "1")
  private Long clienteId;

  @Schema(description = "Valor do investimento inicial em reais", minimum = "100.00")
  private Double valor;

  @Schema(description = "Prazo do investimento em meses", minimum = "1", maximum = "360")
  private Integer prazoMeses;

  @Schema(description = "Tipo de produto (ex: RENDA_FIXA, ACOES, FUNDOS)", minLength = 3, maxLength = 50)
  private String tipoProduto;
}
