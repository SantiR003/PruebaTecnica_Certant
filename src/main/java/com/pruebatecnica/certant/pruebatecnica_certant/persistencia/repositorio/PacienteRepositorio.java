package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.PacienteEntidad;

public interface PacienteRepositorio extends CrudRepository<PacienteEntidad, Long> {

}
