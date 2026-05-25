package org.bubbleplat.academico.service;

import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.NotaRequest;
import org.bubbleplat.academico.dto.NotaResponse;
import org.bubbleplat.academico.entity.Nota;
import org.bubbleplat.academico.repository.NotaRepository;
import org.bubbleplat.academico.repository.EvaluacionRepository;
import org.bubbleplat.academico.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotaService {

    private final NotaRepository notaRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final EstudianteRepository estudianteRepository;

    @Transactional(readOnly = true)
    public List<NotaResponse> findAll() {
        return notaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public NotaResponse findById(Integer id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con id: " + id));
        return toResponse(nota);
    }

    @Transactional(readOnly = true)
    public List<NotaResponse> findByEstudianteId(Integer estudianteId) {
        return notaRepository.findByEstudianteId(estudianteId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotaResponse> findByEvaluacionId(Integer evaluacionId) {
        return notaRepository.findByEvaluacionId(evaluacionId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public NotaResponse create(NotaRequest request) {
        var evaluacion = evaluacionRepository.findById(request.getEvaluacionId())
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con id: " + request.getEvaluacionId()));

        Nota nota = new Nota();
        nota.setEstudianteId(request.getEstudianteId());
        nota.setValor(request.getValor());
        nota.setEvaluacion(evaluacion);
        return toResponse(notaRepository.save(nota));
    }

    @Transactional
    public NotaResponse update(Integer id, NotaRequest request) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada con id: " + id));

        var evaluacion = evaluacionRepository.findById(request.getEvaluacionId())
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con id: " + request.getEvaluacionId()));

        nota.setEstudianteId(request.getEstudianteId());
        nota.setValor(request.getValor());
        nota.setEvaluacion(evaluacion);
        return toResponse(notaRepository.save(nota));
    }

    @Transactional
    public void delete(Integer id) {
        if (!notaRepository.existsById(id)) {
            throw new RuntimeException("Nota no encontrada con id: " + id);
        }
        notaRepository.deleteById(id);
    }

    private NotaResponse toResponse(Nota nota) {
        String estudianteNombre = Optional.ofNullable(nota.getEstudianteId())
                .flatMap(estudianteRepository::findById)
                .map(e -> e.getNombre())
                .orElse(null);
        
        return new NotaResponse(
                nota.getId(),
                nota.getEstudianteId(),
                estudianteNombre,
                nota.getValor(),
                nota.getEvaluacion().getId(),
                nota.getEvaluacion().getNombre()
        );
    }
}
