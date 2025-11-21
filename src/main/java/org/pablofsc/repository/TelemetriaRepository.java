package org.pablofsc.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.entity.TelemetriaEntity;

@ApplicationScoped
public class TelemetriaRepository implements PanacheRepository<TelemetriaEntity> {
}
