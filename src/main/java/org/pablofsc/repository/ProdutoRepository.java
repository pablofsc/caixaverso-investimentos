package org.pablofsc.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.entity.ProdutoEntity;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepositoryBase<ProdutoEntity, Long> {

  public ProdutoEntity findByTipo(String tipo) {
    return find("tipo", tipo).firstResult();
  }
}
