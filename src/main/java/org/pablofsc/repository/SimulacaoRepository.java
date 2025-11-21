package org.pablofsc.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.entity.SimulacaoEntity;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepositoryBase<SimulacaoEntity, Long> {

}
