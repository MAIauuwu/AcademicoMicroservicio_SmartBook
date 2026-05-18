package org.bubbleplat.academico.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.NotaRequest;
import org.bubbleplat.academico.dto.NotaResponse;
import org.bubbleplat.academico.service.NotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;

    @GetMapping
    public ResponseEntity<List<NotaResponse>> findAll() {
        return ResponseEntity.ok(notaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(notaService.findById(id));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<NotaResponse>> findByEstudianteId(@PathVariable Integer estudianteId) {
        return ResponseEntity.ok(notaService.findByEstudianteId(estudianteId));
    }

    @GetMapping("/evaluacion/{evaluacionId}")
    public ResponseEntity<List<NotaResponse>> findByEvaluacionId(@PathVariable Integer evaluacionId) {
        return ResponseEntity.ok(notaService.findByEvaluacionId(evaluacionId));
    }

    @PostMapping
    public ResponseEntity<NotaResponse> create(@Valid @RequestBody NotaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaResponse> update(@PathVariable Integer id, @Valid @RequestBody NotaRequest request) {
        return ResponseEntity.ok(notaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        notaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
