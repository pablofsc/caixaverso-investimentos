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
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoEntity {

  @Id
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private String tipo;

  private Double rentabilidade;

  private String risco;
}
