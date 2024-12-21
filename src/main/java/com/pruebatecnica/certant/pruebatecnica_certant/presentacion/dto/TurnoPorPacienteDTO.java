package com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.pruebatecnica.certant.pruebatecnica_certant.constante.EstadoTurno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoPorPacienteDTO {
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaTurno;
    private LocalTime horaTurno;
    private String motivoVisita;
    EstadoTurno estadoTurno;
    private String nombreProfesional;
    private String apellidoProfesional;
}
