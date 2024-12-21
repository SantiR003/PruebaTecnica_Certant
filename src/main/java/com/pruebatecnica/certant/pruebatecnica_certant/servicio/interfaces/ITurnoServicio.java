package com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces;

import java.util.List;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.TurnoEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorEspecialidadYProfesionalDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorPacienteDTO;

public interface ITurnoServicio {

    List<TurnoDTO> findAll();

    List<TurnoPorPacienteDTO> findByPacienteId(Long id);

    List<TurnoPorEspecialidadYProfesionalDTO> findByEspecialidadAndProfesionalId(Long id, Long id2);

    void save(TurnoEntidad turnosEntidad);

    void update(TurnoEntidad turnosEntidad, Long id);

    void delete(Long id);
}
