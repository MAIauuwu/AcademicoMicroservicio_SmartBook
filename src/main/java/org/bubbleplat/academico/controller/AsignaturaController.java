package org.bubbleplat.academico.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bubbleplat.academico.dto.AsignaturaRequest;
import org.bubbleplat.academico.dto.AsignaturaResponse;
import org.bubbleplat.academico.service.AsignaturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
@RequiredArgsConstructor
public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    @GetMapping
    public ResponseEntity<List<AsignaturaResponse>> findAll() {
        return ResponseEntity.ok(asignaturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(asignaturaService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<AsignaturaResponse> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(asignaturaService.findByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<AsignaturaResponse> create(@Valid @RequestBody AsignaturaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignaturaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignaturaResponse> update(@PathVariable Integer id, @Valid @RequestBody AsignaturaRequest request) {
        return ResponseEntity.ok(asignaturaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        asignaturaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
