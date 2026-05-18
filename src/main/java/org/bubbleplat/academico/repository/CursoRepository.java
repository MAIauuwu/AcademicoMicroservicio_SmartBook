package org.bubbleplat.academico.repository;

import org.bubbleplat.academico.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    List<Curso> findByAnio(Integer anio);

    List<Curso> findByPeriodo(String periodo);
}
