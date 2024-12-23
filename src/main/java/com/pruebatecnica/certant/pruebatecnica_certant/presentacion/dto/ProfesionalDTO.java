package com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfesionalDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private LocalTime inicioJornada;
    private LocalTime finalizacionJornada;
    private String especialidad;
}
