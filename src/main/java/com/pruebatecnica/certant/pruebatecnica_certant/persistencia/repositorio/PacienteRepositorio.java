package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.PacienteEntidad;

public interface PacienteRepositorio extends CrudRepository<PacienteEntidad, Long> {

    @Query("select p from PacienteEntidad p where p.dni = ?1")
    Optional<PacienteEntidad> findByDni(String dni);
}
