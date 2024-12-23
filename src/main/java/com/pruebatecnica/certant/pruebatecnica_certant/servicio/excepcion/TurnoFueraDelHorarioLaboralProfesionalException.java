package com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion;

public class TurnoFueraDelHorarioLaboralProfesionalException extends RuntimeException {
    public TurnoFueraDelHorarioLaboralProfesionalException(String mensaje) {
        super(mensaje);
    }
}
