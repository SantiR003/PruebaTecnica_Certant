package com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces;

import java.util.List;

import com.pruebatecnica.certant.pruebatecnica_certant.constante.EstadoTurno;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.TurnoEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorEspecialidadYProfesionalDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorPacienteDTO;

public interface ITurnoServicio {

    List<TurnoDTO> findAll();

    TurnoDTO findById(Long id);

    List<TurnoPorPacienteDTO> findByPacienteId(Long id);

    void intermediario(TurnoDTO turnoDTO);

    List<TurnoPorEspecialidadYProfesionalDTO> findByEspecialidadId(Long id);

    List<TurnoPorEspecialidadYProfesionalDTO> findByProfesionalId(Long id);

    void save(TurnoEntidad turnosEntidad);

    void update(TurnoDTO turnosDTO, Long id);

    void cambiarEstadoTurno(Long id, EstadoTurno estadoTurno);
}
