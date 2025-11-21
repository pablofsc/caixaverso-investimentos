package org.pablofsc.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "investimentos")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestimentoEntity extends PanacheEntityBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable = false)
  private ClienteEntity cliente;

  @ManyToOne
  @JoinColumn(name = "produto_id", nullable = false)
  private ProdutoEntity produto;

  @Column(nullable = false)
  private Double valor;

  @Column(nullable = false)
  private LocalDate data;
}
