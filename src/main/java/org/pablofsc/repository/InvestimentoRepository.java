package org.pablofsc.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.entity.InvestimentoEntity;

@ApplicationScoped
public class InvestimentoRepository implements PanacheRepository<InvestimentoEntity> {
}
