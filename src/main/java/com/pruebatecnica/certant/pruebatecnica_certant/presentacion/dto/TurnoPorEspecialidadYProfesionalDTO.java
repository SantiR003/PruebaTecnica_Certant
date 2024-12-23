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
public class TurnoPorEspecialidadYProfesionalDTO {
    private String nombreEspecialidad;
    private LocalDate fechaTurno;
    private LocalTime horaTurno;
    private String nombreCompletoPaciente;
    private EstadoTurno estadoTurno;
    private String nombreCompletoProfesional;
}
