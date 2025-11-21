package org.pablofsc.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.pablofsc.domain.entity.UsuarioEntity;

import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioEntity> {

  public Optional<UsuarioEntity> findByEmail(String email) {
    return find("email", email).firstResultOptional();
  }
}
