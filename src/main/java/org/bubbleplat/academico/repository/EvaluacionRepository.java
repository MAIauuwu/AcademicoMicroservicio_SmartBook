package org.bubbleplat.academico.repository;

import org.bubbleplat.academico.entity.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

    List<Evaluacion> findByCursoAsignaturaId(Integer cursoAsignaturaId);
}
