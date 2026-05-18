package org.bubbleplat.academico.service;

import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.AsignaturaRequest;
import org.bubbleplat.academico.dto.AsignaturaResponse;
import org.bubbleplat.academico.entity.Asignatura;
import org.bubbleplat.academico.repository.AsignaturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;

    @Transactional(readOnly = true)
    public List<AsignaturaResponse> findAll() {
        return asignaturaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AsignaturaResponse findById(Integer id) {
        Asignatura asignatura = asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con id: " + id));
        return toResponse(asignatura);
    }

    @Transactional(readOnly = true)
    public AsignaturaResponse findByNombre(String nombre) {
        Asignatura asignatura = asignaturaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con nombre: " + nombre));
        return toResponse(asignatura);
    }

    @Transactional
    public AsignaturaResponse create(AsignaturaRequest request) {
        Asignatura asignatura = new Asignatura();
        asignatura.setNombre(request.getNombre());
        asignatura.setDescripcion(request.getDescripcion());
        asignatura.setCreditos(request.getCreditos());
        return toResponse(asignaturaRepository.save(asignatura));
    }

    @Transactional
    public AsignaturaResponse update(Integer id, AsignaturaRequest request) {
        Asignatura asignatura = asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con id: " + id));
        asignatura.setNombre(request.getNombre());
        asignatura.setDescripcion(request.getDescripcion());
        asignatura.setCreditos(request.getCreditos());
        return toResponse(asignaturaRepository.save(asignatura));
    }

    @Transactional
    public void delete(Integer id) {
        if (!asignaturaRepository.existsById(id)) {
            throw new RuntimeException("Asignatura no encontrada con id: " + id);
        }
        asignaturaRepository.deleteById(id);
    }

    private AsignaturaResponse toResponse(Asignatura asignatura) {
        return new AsignaturaResponse(
                asignatura.getId(),
                asignatura.getNombre(),
                asignatura.getDescripcion(),
                asignatura.getCreditos()
        );
    }
}
