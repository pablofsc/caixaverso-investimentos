package org.pablofsc.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteEntity {

  @Id
  private Long id;

  @Column(nullable = false)
  private String nome;

  // Perfil do cliente
  @Column(name = "prazo_medio_preferido")
  private Integer prazoMedioPreferido; // em meses (ex: 12 = um ano)

  @Column(name = "preferencia_liquidez_rentabilidade")
  private String preferenciaRentLiq; // "LIQUIDEZ", "EQUILIBRIO", "RENTABILIDADE"

  @Column(name = "risco_maximo_aceitavel")
  private String riscoMaximoAceitavel; // "Muito Baixo", "Baixo", "Alto", "Muito Alto"

  @Column(name = "volume_total_investido")
  private Double volumeTotalInvestido; // em R$

  @Column(name = "frequencia_movimentacoes")
  private String frequenciaMovimentacoes; // "BAIXA", "MEDIA", "ALTA"
}
