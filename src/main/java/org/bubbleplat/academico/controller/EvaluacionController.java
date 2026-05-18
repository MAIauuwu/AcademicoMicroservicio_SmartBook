package org.bubbleplat.academico.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.EvaluacionRequest;
import org.bubbleplat.academico.dto.EvaluacionResponse;
import org.bubbleplat.academico.service.EvaluacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @GetMapping
    public ResponseEntity<List<EvaluacionResponse>> findAll() {
        return ResponseEntity.ok(evaluacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(evaluacionService.findById(id));
    }

    @GetMapping("/curso-asignatura/{cursoAsignaturaId}")
    public ResponseEntity<List<EvaluacionResponse>> findByCursoAsignaturaId(@PathVariable Integer cursoAsignaturaId) {
        return ResponseEntity.ok(evaluacionService.findByCursoAsignaturaId(cursoAsignaturaId));
    }

    @PostMapping
    public ResponseEntity<EvaluacionResponse> create(@Valid @RequestBody EvaluacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionResponse> update(@PathVariable Integer id, @Valid @RequestBody EvaluacionRequest request) {
        return ResponseEntity.ok(evaluacionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        evaluacionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
