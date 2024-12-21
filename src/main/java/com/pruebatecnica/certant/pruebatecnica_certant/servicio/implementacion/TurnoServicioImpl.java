package com.pruebatecnica.certant.pruebatecnica_certant.servicio.implementacion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.ProfesionalEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.TurnoEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio.ProfesionalRepositorio;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio.TurnoRepositorio;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorEspecialidadYProfesionalDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorPacienteDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion.TurnoInvalidoException;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces.ITurnoServicio;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TurnoServicioImpl implements ITurnoServicio {

    @Autowired
    TurnoRepositorio turnoRepositorio;
    @Autowired
    ProfesionalRepositorio profesionalRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<TurnoDTO> findAll() {

        List<TurnoEntidad> listaTurnos = (List<TurnoEntidad>) turnoRepositorio.findAll();

        if (listaTurnos.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<TurnoDTO> listaTurnosDTO = listaTurnos.stream().map(turno -> {
                TurnoDTO turnoDTO = new TurnoDTO();
                turnoDTO.setNombre(turno.getPacienteEntidad().getNombre());
                turnoDTO.setApellido(turno.getPacienteEntidad().getApellido());
                turnoDTO.setDni(turno.getPacienteEntidad().getDni());
                turnoDTO.setFechaTurno(turno.getFechaTurno());
                turnoDTO.setHoraTurno(turno.getHoraTurno());
                turnoDTO.setEstadoTurno(turno.getEstadoTurno());
                turnoDTO.setMotivoVisita(turno.getMotivoVisita());
                turnoDTO.setNombreProfesional(turno.getProfesionalEntidad().getNombre());
                turnoDTO.setApellidoProfesional(turno.getProfesionalEntidad().getApellido());
                turnoDTO.setConsultorio(turno.getProfesionalEntidad().getConsultorio());
                turnoDTO.setEspecialidad(turno.getProfesionalEntidad().getEspecialidad().getNombre());
                return turnoDTO;
            }).toList();

            return listaTurnosDTO;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(TurnoEntidad turnosEntidad) {
        if (VerificarHorarioYFecha(turnosEntidad) && ValidarDisponibilidadTurno(turnosEntidad)) {
            turnoRepositorio.save(turnosEntidad);
        } else {
            throw new TurnoInvalidoException("Datos del turno invalidos");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TurnoEntidad turnosEntidad, Long id) {
        if (ValidarHorarioTurno(id)) {
            try {
                TurnoEntidad turnoDesactualizado = turnoRepositorio.findById(id).orElse(null);

                if (turnoDesactualizado == null) {
                    throw new RuntimeException("Turno no encontrado: " + id);
                }
                turnoDesactualizado.setFechaTurno(turnosEntidad.getFechaTurno());
                turnoDesactualizado.setHoraTurno(turnosEntidad.getHoraTurno());
                turnoDesactualizado.setMotivoVisita(turnosEntidad.getMotivoVisita());
                turnoDesactualizado.setEstadoTurno(turnosEntidad.getEstadoTurno());
                turnoDesactualizado.setPacienteEntidad(turnosEntidad.getPacienteEntidad());
                turnoDesactualizado.setProfesionalEntidad(turnosEntidad.getProfesionalEntidad());
                turnoRepositorio.save(turnoDesactualizado);
            } catch (Exception e) {
                throw new RuntimeException("Ocurrio un error al actualizar el turno: " + id);
            }
        } else {
            log.error("Accion invalida a menos de una hora de efectuar el turno ", Exception.class);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (ValidarHorarioTurno(id)) {
            try {
                TurnoEntidad turnoAEliminar = turnoRepositorio.findById(id).orElse(null);

                if (turnoAEliminar == null) {
                    throw new RuntimeException("Turno no encontrado: " + id);
                }
                turnoRepositorio.delete(turnoAEliminar);
            } catch (Exception e) {
                throw new RuntimeException("Ocurrio un error al eliminar el turno: " + id);
            }
        } else {
            log.error("Accion invalida a menos de una hora de efectuar el turno ", Exception.class);
        }
    }

    @Override
    public List<TurnoPorPacienteDTO> findByPacienteId(Long id) {
        List<TurnoEntidad> listaTurnos = turnoRepositorio.findByPacienteId(id);
        if (listaTurnos.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<TurnoPorPacienteDTO> listaPacienteTurnoDTO = listaTurnos.stream().map(turno -> {
                TurnoPorPacienteDTO pacienteTurnoDTO = new TurnoPorPacienteDTO();
                pacienteTurnoDTO.setNombre(turno.getPacienteEntidad().getNombre());
                pacienteTurnoDTO.setApellido(turno.getPacienteEntidad().getApellido());
                pacienteTurnoDTO.setDni(turno.getPacienteEntidad().getDni());
                pacienteTurnoDTO.setFechaTurno(turno.getFechaTurno());
                pacienteTurnoDTO.setHoraTurno(turno.getHoraTurno());
                pacienteTurnoDTO.setEstadoTurno(turno.getEstadoTurno());
                pacienteTurnoDTO.setMotivoVisita(turno.getMotivoVisita());
                pacienteTurnoDTO.setNombreProfesional(turno.getProfesionalEntidad().getNombre());
                pacienteTurnoDTO.setApellidoProfesional(turno.getProfesionalEntidad().getApellido());
                return pacienteTurnoDTO;
            }).toList();
            return listaPacienteTurnoDTO;
        }
    }

    @Override
    public List<TurnoPorEspecialidadYProfesionalDTO> findByEspecialidadAndProfesionalId(Long id, Long id2) {
        List<TurnoEntidad> listaTurnos = turnoRepositorio.findByEspecialidadAndProfesionalId(id, id2);
        if (listaTurnos.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<TurnoPorEspecialidadYProfesionalDTO> listaPacienteTurnoDTO = listaTurnos.stream().map(turno -> {
                TurnoPorEspecialidadYProfesionalDTO especialidadYProfesionalDTO = new TurnoPorEspecialidadYProfesionalDTO();
                especialidadYProfesionalDTO
                        .setNombreEspecialidad(turno.getProfesionalEntidad().getEspecialidad().getNombre());
                especialidadYProfesionalDTO.setNombreProfesional(turno.getProfesionalEntidad().getNombre());
                especialidadYProfesionalDTO.setApellidoProfesional(turno.getProfesionalEntidad().getApellido());
                especialidadYProfesionalDTO.setFechaTurno(turno.getFechaTurno());
                especialidadYProfesionalDTO.setHoraTurno(turno.getHoraTurno());
                especialidadYProfesionalDTO.setMotivoVisita(turno.getMotivoVisita());
                especialidadYProfesionalDTO.setEstadoTurno(turno.getEstadoTurno());
                return especialidadYProfesionalDTO;
            }).toList();
            return listaPacienteTurnoDTO;
        }
    }

    // region Validaciones

    private Boolean VerificarHorarioYFecha(TurnoEntidad turnosEntidad) {

        LocalDate actualidad = LocalDate.now();

        ProfesionalEntidad profesionalEntidad = profesionalRepositorio
                .findById(turnosEntidad.getProfesionalEntidad().getId())
                .orElse(null);
        ;

        boolean validacionDia = !turnosEntidad.getFechaTurno().getDayOfWeek().equals(DayOfWeek.SUNDAY)
                && turnosEntidad.getFechaTurno().isAfter(actualidad)
                || turnosEntidad.getFechaTurno().isEqual(actualidad);
        // Verificar que la fecha no es un domingo
        // Verificar que la fecha del turno es despues de la fecha actual o el mismo dia
        // actual.

        if (!validacionDia) {
            log.error("No se puede dar un turno fuera de los dias habiles de la clinica", Exception.class);
        }

        boolean validacionDiaProfesional = profesionalEntidad.getDiasLaborales()
                .stream().anyMatch(dia -> dia.getDia().equals(turnosEntidad.getFechaTurno().getDayOfWeek().name()));

        if (!validacionDiaProfesional) {
            log.error("No se puede dar un turno fuera de los dias laborales del profesional", Exception.class);
        }

        boolean validacionHora = profesionalEntidad.getInicioJornada().isBefore(turnosEntidad.getHoraTurno())
                && profesionalEntidad.getFinalizacionJornada()
                        .isAfter(turnosEntidad.getHoraTurno());

        if (!validacionHora) {
            log.error("No se puede dar un turno fuera del horario laboral del profesional", Exception.class);
        }

        boolean validarHoraTurno = profesionalEntidad.getHorariosProfesional().stream()
                .anyMatch(horario -> horario.getInicioTurno().equals(turnosEntidad.getHoraTurno()));

        if (!validarHoraTurno) {
            log.error("No se puede dar un turno que no coincida con los horarios establecidos", Exception.class);
        }
        return validacionDia && validacionDiaProfesional && validacionHora && validarHoraTurno;
    }

    private Boolean ValidarDisponibilidadTurno(TurnoEntidad turnoEntidad) {

        List<TurnoEntidad> listaTurnos = (List<TurnoEntidad>) turnoRepositorio.findAll();

        boolean validacionDisponibilidad = listaTurnos.stream()
                .anyMatch(turno -> turno.getFechaTurno().equals(turnoEntidad.getFechaTurno())
                        && turno.getHoraTurno().equals(turnoEntidad.getHoraTurno()));

        if (validacionDisponibilidad) {
            log.error("El turno solicitado ya fue dado para esa fecha y hora", Exception.class);
            return false;
        }

        return true;
    }

    private Boolean ValidarHorarioTurno(Long id) {
        TurnoEntidad turnoEntidad = turnoRepositorio.findById(id).orElse(null);
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime fechaHoraTurno = LocalDateTime.of(turnoEntidad.getFechaTurno(), turnoEntidad.getHoraTurno());
        // Convierto la hora y fecha del turno en uno solo para comparar correctamente
        // la condicion
        if (fechaHoraTurno.isBefore(localDateTime.minusHours(1))) {
            return true;
        }
        return false;
    }

    // endregion

}
