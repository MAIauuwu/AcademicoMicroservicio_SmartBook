package org.bubbleplat.academico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaResponse {

    private Integer id;
    private Integer estudianteId;
    private Double valor;
    private Integer evaluacionId;
    private String evaluacionNombre;
}
