package org.bubbleplat.academico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoAsignaturaResponse {

    private Integer id;
    private Integer cursoId;
    private String cursoNombre;
    private Integer asignaturaId;
    private String asignaturaNombre;
    private Integer docenteId;
    private String semestre;
}
