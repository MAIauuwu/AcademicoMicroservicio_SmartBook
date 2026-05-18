package org.bubbleplat.academico.repository;

import org.bubbleplat.academico.entity.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Integer> {

    Optional<Asignatura> findByNombre(String nombre);
}
