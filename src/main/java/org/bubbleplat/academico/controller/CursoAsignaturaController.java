package org.bubbleplat.academico.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.CursoAsignaturaRequest;
import org.bubbleplat.academico.dto.CursoAsignaturaResponse;
import org.bubbleplat.academico.service.CursoAsignaturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos-asignaturas")
@RequiredArgsConstructor
public class CursoAsignaturaController {

    private final CursoAsignaturaService cursoAsignaturaService;

    @GetMapping
    public ResponseEntity<List<CursoAsignaturaResponse>> findAll() {
        return ResponseEntity.ok(cursoAsignaturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoAsignaturaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(cursoAsignaturaService.findById(id));
    }

    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<CursoAsignaturaResponse>> findByDocenteId(@PathVariable Integer docenteId) {
        return ResponseEntity.ok(cursoAsignaturaService.findByDocenteId(docenteId));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<CursoAsignaturaResponse>> findByCursoId(@PathVariable Integer cursoId) {
        return ResponseEntity.ok(cursoAsignaturaService.findByCursoId(cursoId));
    }

    @GetMapping("/semestre/{semestre}")
    public ResponseEntity<List<CursoAsignaturaResponse>> findBySemestre(@PathVariable String semestre) {
        return ResponseEntity.ok(cursoAsignaturaService.findBySemestre(semestre));
    }

    @PostMapping
    public ResponseEntity<CursoAsignaturaResponse> create(@Valid @RequestBody CursoAsignaturaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoAsignaturaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoAsignaturaResponse> update(@PathVariable Integer id, @Valid @RequestBody CursoAsignaturaRequest request) {
        return ResponseEntity.ok(cursoAsignaturaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cursoAsignaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
