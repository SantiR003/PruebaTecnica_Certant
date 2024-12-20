package com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces;

import java.util.List;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.ProfesionalEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.ProfesionalDTO;

public interface IProfesionalServicio {

    List<ProfesionalDTO> findAll();

    ProfesionalEntidad findById(Long id);

}
