package org.bubbleplat.academico.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaRequest {

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Integer estudianteId;

    @NotNull(message = "El valor de la nota es obligatorio")
    @Positive(message = "El valor de la nota debe ser positivo")
    private Double valor;

    @NotNull(message = "El ID de la evaluación es obligatorio")
    private Integer evaluacionId;
}
