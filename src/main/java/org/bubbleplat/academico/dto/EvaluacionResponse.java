package org.bubbleplat.academico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private Double peso;
    private Double notaMaxima;
    private Integer cursoAsignaturaId;
}
