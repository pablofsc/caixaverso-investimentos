package org.pablofsc.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.entity.ClienteEntity;

@ApplicationScoped
public class ClienteRepository implements PanacheRepositoryBase<ClienteEntity, Long> {
}
