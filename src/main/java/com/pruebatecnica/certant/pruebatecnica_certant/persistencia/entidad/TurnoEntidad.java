package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad;

import java.time.LocalDate;
import java.time.LocalTime;

import com.pruebatecnica.certant.pruebatecnica_certant.constante.EstadoTurno;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Table(name = "turno")
@AllArgsConstructor
@Data
@Valid
@Builder
public class TurnoEntidad {
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

    @Column(name = "estado_turno", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    EstadoTurno estadoTurno;

    @ManyToOne(targetEntity = ProfesionalEntidad.class)
    @JoinColumn(name = "profesional_id", nullable = false)
    ProfesionalEntidad profesionalEntidad;

    @ManyToOne(targetEntity = PacienteEntidad.class)
    PacienteEntidad pacienteEntidad;

    public TurnoEntidad() {
        this.estadoTurno = EstadoTurno.INICIADO;
    }

}
