package org.bubbleplat.academico.repository;

import org.bubbleplat.academico.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {

    List<Nota> findByEstudianteId(Integer estudianteId);

    List<Nota> findByEvaluacionId(Integer evaluacionId);
}
