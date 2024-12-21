package com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion;

public class TurnoInvalidoException extends RuntimeException {
    public TurnoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
