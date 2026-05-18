package org.bubbleplat.academico.service;

import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.EvaluacionRequest;
import org.bubbleplat.academico.dto.EvaluacionResponse;
import org.bubbleplat.academico.entity.Evaluacion;
import org.bubbleplat.academico.repository.EvaluacionRepository;
import org.bubbleplat.academico.repository.CursoAsignaturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final CursoAsignaturaRepository cursoAsignaturaRepository;

    @Transactional(readOnly = true)
    public List<EvaluacionResponse> findAll() {
        return evaluacionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EvaluacionResponse findById(Integer id) {
        Evaluacion evaluacion = evaluacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con id: " + id));
        return toResponse(evaluacion);
    }

    @Transactional(readOnly = true)
    public List<EvaluacionResponse> findByCursoAsignaturaId(Integer cursoAsignaturaId) {
        return evaluacionRepository.findByCursoAsignaturaId(cursoAsignaturaId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public EvaluacionResponse create(EvaluacionRequest request) {
        var cursoAsignatura = cursoAsignaturaRepository.findById(request.getCursoAsignaturaId())
                .orElseThrow(() -> new RuntimeException("Curso-Asignatura no encontrado con id: " + request.getCursoAsignaturaId()));

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setNombre(request.getNombre());
        evaluacion.setDescripcion(request.getDescripcion());
        evaluacion.setFecha(request.getFecha());
        evaluacion.setPeso(request.getPeso());
        evaluacion.setNotaMaxima(request.getNotaMaxima());
        evaluacion.setCursoAsignatura(cursoAsignatura);
        return toResponse(evaluacionRepository.save(evaluacion));
    }

    @Transactional
    public EvaluacionResponse update(Integer id, EvaluacionRequest request) {
        Evaluacion evaluacion = evaluacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con id: " + id));

        var cursoAsignatura = cursoAsignaturaRepository.findById(request.getCursoAsignaturaId())
                .orElseThrow(() -> new RuntimeException("Curso-Asignatura no encontrado con id: " + request.getCursoAsignaturaId()));

        evaluacion.setNombre(request.getNombre());
        evaluacion.setDescripcion(request.getDescripcion());
        evaluacion.setFecha(request.getFecha());
        evaluacion.setPeso(request.getPeso());
        evaluacion.setNotaMaxima(request.getNotaMaxima());
        evaluacion.setCursoAsignatura(cursoAsignatura);
        return toResponse(evaluacionRepository.save(evaluacion));
    }

    @Transactional
    public void delete(Integer id) {
        if (!evaluacionRepository.existsById(id)) {
            throw new RuntimeException("Evaluación no encontrada con id: " + id);
        }
        evaluacionRepository.deleteById(id);
    }

    private EvaluacionResponse toResponse(Evaluacion e) {
        return new EvaluacionResponse(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getFecha(),
                e.getPeso(),
                e.getNotaMaxima(),
                e.getCursoAsignatura().getId()
        );
    }
}
