package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "turno")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Valid
@Builder
public class TurnosEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_turno", nullable = false)
    @NotNull
    private LocalDate fechaTurno;
    @Column(name = "hora_turno", nullable = false)
    @NotNull
    private LocalTime horaTurno;
    @Column(name = "motivo_visita", nullable = false)
    private String motivoVisita;

    @ManyToOne(targetEntity = ProfesionalEntidad.class)
    @JoinColumn(name = "profesional_id", nullable = false)
    ProfesionalEntidad profesionalEntidad;

    @ManyToOne(targetEntity = PacienteEntidad.class)
    PacienteEntidad pacienteEntidad;
}
