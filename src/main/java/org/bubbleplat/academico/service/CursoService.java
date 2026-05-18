package org.bubbleplat.academico.service;

import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.CursoRequest;
import org.bubbleplat.academico.dto.CursoResponse;
import org.bubbleplat.academico.entity.Curso;
import org.bubbleplat.academico.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    @Transactional(readOnly = true)
    public List<CursoResponse> findAll() {
        return cursoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CursoResponse findById(Integer id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
        return toResponse(curso);
    }

    @Transactional(readOnly = true)
    public List<CursoResponse> findByAnio(Integer anio) {
        return cursoRepository.findByAnio(anio).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CursoResponse> findByPeriodo(String periodo) {
        return cursoRepository.findByPeriodo(periodo).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CursoResponse create(CursoRequest request) {
        Curso curso = new Curso();
        curso.setNombre(request.getNombre());
        curso.setDescripcion(request.getDescripcion());
        curso.setAnio(request.getAnio());
        curso.setPeriodo(request.getPeriodo());
        return toResponse(cursoRepository.save(curso));
    }

    @Transactional
    public CursoResponse update(Integer id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
        curso.setNombre(request.getNombre());
        curso.setDescripcion(request.getDescripcion());
        curso.setAnio(request.getAnio());
        curso.setPeriodo(request.getPeriodo());
        return toResponse(cursoRepository.save(curso));
    }

    @Transactional
    public void delete(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado con id: " + id);
        }
        cursoRepository.deleteById(id);
    }

    private CursoResponse toResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getAnio(),
                curso.getPeriodo()
        );
    }
}
