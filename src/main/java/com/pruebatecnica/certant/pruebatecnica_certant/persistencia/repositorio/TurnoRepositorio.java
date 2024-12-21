package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.TurnoEntidad;

public interface TurnoRepositorio extends CrudRepository<TurnoEntidad, Long> {

    @Query("select t from TurnoEntidad t where t.pacienteEntidad.id = ?1")
    List<TurnoEntidad> findByPacienteId(Long id);

    @Query("select t from TurnoEntidad t where t.profesionalEntidad.id = ?1 and t.profesionalEntidad.especialidad.id = ?2")
    List<TurnoEntidad> findByEspecialidadAndProfesionalId(Long id, Long id2);
}
