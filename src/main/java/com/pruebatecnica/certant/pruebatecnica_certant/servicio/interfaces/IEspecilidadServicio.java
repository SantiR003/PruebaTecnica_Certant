package com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces;

import java.util.List;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.EspecialidadEntidad;

public interface IEspecilidadServicio {

    List<String> findAll();

    EspecialidadEntidad findById(Long id);

}
