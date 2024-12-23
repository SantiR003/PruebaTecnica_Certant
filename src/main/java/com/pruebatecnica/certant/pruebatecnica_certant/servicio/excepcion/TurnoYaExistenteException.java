package com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion;

public class TurnoYaExistenteException extends RuntimeException {

    public TurnoYaExistenteException(String mensaje) {
        super(mensaje);
    }

}
