package org.bubbleplat.academico.repository;

import org.bubbleplat.academico.entity.CursoAsignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoAsignaturaRepository extends JpaRepository<CursoAsignatura, Integer> {

    List<CursoAsignatura> findByDocenteId(Integer docenteId);

    List<CursoAsignatura> findByCursoId(Integer cursoId);

    List<CursoAsignatura> findByAsignaturaId(Integer asignaturaId);

    List<CursoAsignatura> findBySemestre(String semestre);
}
