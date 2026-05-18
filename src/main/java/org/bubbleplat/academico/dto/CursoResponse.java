package org.bubbleplat.academico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer anio;
    private String periodo;
}
