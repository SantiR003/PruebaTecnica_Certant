package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.ProfesionalEntidad;

public interface ProfesionalRepositorio extends CrudRepository<ProfesionalEntidad, Long> {

}
