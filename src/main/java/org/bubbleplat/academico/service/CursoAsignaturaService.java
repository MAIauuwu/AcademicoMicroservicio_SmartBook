package org.bubbleplat.academico.service;

import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.CursoAsignaturaRequest;
import org.bubbleplat.academico.dto.CursoAsignaturaResponse;
import org.bubbleplat.academico.entity.CursoAsignatura;
import org.bubbleplat.academico.repository.CursoAsignaturaRepository;
import org.bubbleplat.academico.repository.CursoRepository;
import org.bubbleplat.academico.repository.AsignaturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoAsignaturaService {

    private final CursoAsignaturaRepository cursoAsignaturaRepository;
    private final CursoRepository cursoRepository;
    private final AsignaturaRepository asignaturaRepository;

    @Transactional(readOnly = true)
    public List<CursoAsignaturaResponse> findAll() {
        return cursoAsignaturaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CursoAsignaturaResponse findById(Integer id) {
        CursoAsignatura cursoAsignatura = cursoAsignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso-Asignatura no encontrado con id: " + id));
        return toResponse(cursoAsignatura);
    }

    @Transactional(readOnly = true)
    public List<CursoAsignaturaResponse> findByDocenteId(Integer docenteId) {
        return cursoAsignaturaRepository.findByDocenteId(docenteId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CursoAsignaturaResponse> findByCursoId(Integer cursoId) {
        return cursoAsignaturaRepository.findByCursoId(cursoId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CursoAsignaturaResponse> findBySemestre(String semestre) {
        return cursoAsignaturaRepository.findBySemestre(semestre).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CursoAsignaturaResponse create(CursoAsignaturaRequest request) {
        var curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + request.getCursoId()));
        var asignatura = asignaturaRepository.findById(request.getAsignaturaId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con id: " + request.getAsignaturaId()));

        CursoAsignatura cursoAsignatura = new CursoAsignatura();
        cursoAsignatura.setCurso(curso);
        cursoAsignatura.setAsignatura(asignatura);
        cursoAsignatura.setDocenteId(request.getDocenteId());
        cursoAsignatura.setSemestre(request.getSemestre());
        return toResponse(cursoAsignaturaRepository.save(cursoAsignatura));
    }

    @Transactional
    public CursoAsignaturaResponse update(Integer id, CursoAsignaturaRequest request) {
        CursoAsignatura cursoAsignatura = cursoAsignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso-Asignatura no encontrado con id: " + id));

        var curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + request.getCursoId()));
        var asignatura = asignaturaRepository.findById(request.getAsignaturaId())
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con id: " + request.getAsignaturaId()));

        cursoAsignatura.setCurso(curso);
        cursoAsignatura.setAsignatura(asignatura);
        cursoAsignatura.setDocenteId(request.getDocenteId());
        cursoAsignatura.setSemestre(request.getSemestre());
        return toResponse(cursoAsignaturaRepository.save(cursoAsignatura));
    }

    @Transactional
    public void delete(Integer id) {
        if (!cursoAsignaturaRepository.existsById(id)) {
            throw new RuntimeException("Curso-Asignatura no encontrado con id: " + id);
        }
        cursoAsignaturaRepository.deleteById(id);
    }

    private CursoAsignaturaResponse toResponse(CursoAsignatura ca) {
        return new CursoAsignaturaResponse(
                ca.getId(),
                ca.getCurso().getId(),
                ca.getCurso().getNombre(),
                ca.getAsignatura().getId(),
                ca.getAsignatura().getNombre(),
                ca.getDocenteId(),
                ca.getSemestre()
        );
    }
}
