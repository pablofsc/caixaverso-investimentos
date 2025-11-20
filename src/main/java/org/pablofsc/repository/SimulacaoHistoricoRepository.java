package org.pablofsc.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.entity.SimulacaoHistoricoEntity;

@ApplicationScoped
public class SimulacaoHistoricoRepository implements PanacheRepositoryBase<SimulacaoHistoricoEntity, Long> {

}
