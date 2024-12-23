package com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces;

import java.util.List;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.PacienteEntidad;

public interface IPacienteServicio {

    List<PacienteEntidad> findAll();

    PacienteEntidad findById(Long id);

    PacienteEntidad findByDni(String dni);

    void save(PacienteEntidad pacienteEntidad);

    void update(PacienteEntidad pacienteEntidad, Long id);

    void delete(Long id);

}
