package org.bubbleplat.academico.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.CursoRequest;
import org.bubbleplat.academico.dto.CursoResponse;
import org.bubbleplat.academico.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() {
        return ResponseEntity.ok(cursoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(cursoService.findById(id));
    }

    @GetMapping("/anio/{anio}")
    public ResponseEntity<List<CursoResponse>> findByAnio(@PathVariable Integer anio) {
        return ResponseEntity.ok(cursoService.findByAnio(anio));
    }

    @GetMapping("/periodo/{periodo}")
    public ResponseEntity<List<CursoResponse>> findByPeriodo(@PathVariable String periodo) {
        return ResponseEntity.ok(cursoService.findByPeriodo(periodo));
    }

    @PostMapping
    public ResponseEntity<CursoResponse> create(@Valid @RequestBody CursoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> update(@PathVariable Integer id, @Valid @RequestBody CursoRequest request) {
        return ResponseEntity.ok(cursoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
