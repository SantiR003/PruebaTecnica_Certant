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
public class TurnoDTO {

    private Long id;
    private LocalDate fechaTurno;
    private LocalTime horaTurno;
    private String motivoVisita;
    private Long profesionalId;
    private String pacienteDni;
    private EstadoTurno estadoTurno;
    private String especialidad;
}
