package org.bubbleplat.academico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoAsignaturaRequest {

    @NotNull(message = "El ID del curso es obligatorio")
    private Integer cursoId;

    @NotNull(message = "El ID de la asignatura es obligatorio")
    private Integer asignaturaId;

    @NotNull(message = "El ID del docente es obligatorio")
    private Integer docenteId;

    @NotBlank(message = "El semestre es obligatorio")
    private String semestre;
}
