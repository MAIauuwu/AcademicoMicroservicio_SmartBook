package org.bubbleplat.academico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evaluacion")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private Double notaMaxima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_asignatura_id", nullable = false)
    private CursoAsignatura cursoAsignatura;
}
