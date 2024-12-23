package com.pruebatecnica.certant.pruebatecnica_certant.servicio.implementacion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pruebatecnica.certant.pruebatecnica_certant.constante.EstadoTurno;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.PacienteEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.ProfesionalEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.TurnoEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio.PacienteRepositorio;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio.ProfesionalRepositorio;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio.TurnoRepositorio;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorEspecialidadYProfesionalDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorPacienteDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion.TurnoFueraDeDiasHabilesClinicaException;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion.TurnoFueraDeDiasHabilesProfesionalException;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion.TurnoFueraDeLosHorariosEstablecidosProfesionalException;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion.TurnoFueraDelHorarioLaboralProfesionalException;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion.TurnoInvalidoException;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.excepcion.TurnoYaExistenteException;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces.ITurnoServicio;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TurnoServicioImpl implements ITurnoServicio {

    @Autowired
    TurnoRepositorio turnoRepositorio;
    @Autowired
    ProfesionalRepositorio profesionalRepositorio;
    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<TurnoDTO> findAll() {

        List<TurnoEntidad> listaTurnos = (List<TurnoEntidad>) turnoRepositorio.findAll();

        if (listaTurnos.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<TurnoDTO> listaTurnosDTO = listaTurnos.stream().map(turno -> {
                TurnoDTO turnoDTO = new TurnoDTO();
                turnoDTO.setId(turno.getId());
                turnoDTO.setFechaTurno(turno.getFechaTurno());
                turnoDTO.setHoraTurno(turno.getHoraTurno());
                turnoDTO.setMotivoVisita(turno.getMotivoVisita());
                turnoDTO.setProfesionalId(turno.getProfesionalEntidad().getId());
                turnoDTO.setPacienteDni(turno.getPacienteEntidad().getDni());
                turnoDTO.setEstadoTurno(turno.getEstadoTurno());
                turnoDTO.setEspecialidad(turno.getProfesionalEntidad().getEspecialidad().getNombre());
                return turnoDTO;
            }).toList();

            return listaTurnosDTO;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TurnoDTO findById(Long id) {
        TurnoEntidad turno = turnoRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profesional no encontrado"));

        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setFechaTurno(turno.getFechaTurno());
        turnoDTO.setHoraTurno(turno.getHoraTurno());
        turnoDTO.setMotivoVisita(turno.getMotivoVisita());
        turnoDTO.setProfesionalId(turno.getProfesionalEntidad().getId());
        turnoDTO.setPacienteDni(turno.getPacienteEntidad().getDni());
        return turnoDTO;
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
    public void intermediario(TurnoDTO turnoDTO) {

        ProfesionalEntidad profesional = profesionalRepositorio.findById(turnoDTO.getProfesionalId())
                .orElseThrow(() -> new EntityNotFoundException("Profesional no encontrado"));
        PacienteEntidad paciente = pacienteRepositorio.findByDni(turnoDTO.getPacienteDni())
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        TurnoEntidad turno = new TurnoEntidad();
        turno.setFechaTurno(turnoDTO.getFechaTurno());
        turno.setHoraTurno(turnoDTO.getHoraTurno());
        turno.setMotivoVisita(turnoDTO.getMotivoVisita());
        turno.setProfesionalEntidad(profesional);
        turno.setPacienteEntidad(paciente);

        save(turno);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TurnoDTO turnoDTO, Long id) {
        ProfesionalEntidad profesional = profesionalRepositorio.findById(turnoDTO.getProfesionalId())
                .orElseThrow(() -> new EntityNotFoundException("Profesional no encontrado"));
        PacienteEntidad paciente = pacienteRepositorio.findByDni(turnoDTO.getPacienteDni())
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
        if (ValidarHorarioTurno(id)) {
            try {
                TurnoEntidad turnoDesactualizado = turnoRepositorio.findById(id).orElse(null);
                if (turnoDesactualizado == null) {
                    throw new RuntimeException("Turno no encontrado: " + id);
                }
                turnoDesactualizado.setFechaTurno(turnoDTO.getFechaTurno());
                turnoDesactualizado.setHoraTurno(turnoDTO.getHoraTurno());
                turnoDesactualizado.setMotivoVisita(turnoDTO.getMotivoVisita());
                turnoDesactualizado.setPacienteEntidad(paciente);
                turnoDesactualizado.setProfesionalEntidad(profesional);
                if (VerificarHorarioYFecha(turnoDesactualizado) && ValidarDisponibilidadTurno(turnoDesactualizado)) {
                    turnoRepositorio.save(turnoDesactualizado);
                } else {
                    log.error("Las validaciones de fecha y disponibilidad fallaron.");
                }
            } catch (Exception e) {
                throw new RuntimeException("Ocurrio un error al actualizar el turno: " + id);
            }
        } else {
            log.error("Accion invalida a menos de una hora de efectuar el turno ", Exception.class);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cambiarEstadoTurno(Long id, EstadoTurno estadoTurno) {
        TurnoEntidad turno = turnoRepositorio.findById(id).orElse(null);
        if (turno == null) {
            throw new RuntimeException("Turno no encontrado: " + id);
        }
        if (ValidarHorarioTurno(id)) {
            try {
                turno.setEstadoTurno(estadoTurno);
                save(turno);
            } catch (Exception e) {
                throw new RuntimeException("Ocurrio un error al actualizar el turno: " + id);
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
                pacienteTurnoDTO.setNombreCompletoPaciente(
                        turno.getPacienteEntidad().getNombre() + " " + turno.getPacienteEntidad().getApellido());
                pacienteTurnoDTO.setDni(turno.getPacienteEntidad().getDni());
                pacienteTurnoDTO.setFechaTurno(turno.getFechaTurno());
                pacienteTurnoDTO.setHoraTurno(turno.getHoraTurno());
                pacienteTurnoDTO.setEstadoTurno(turno.getEstadoTurno());
                pacienteTurnoDTO.setMotivoVisita(turno.getMotivoVisita());
                pacienteTurnoDTO.setNombreCompletoProfesional(
                        turno.getProfesionalEntidad().getNombre() + " " + turno.getProfesionalEntidad().getApellido());
                return pacienteTurnoDTO;
            }).toList();
            return listaPacienteTurnoDTO;
        }
    }

    @Override
    public List<TurnoPorEspecialidadYProfesionalDTO> findByEspecialidadId(Long id) {
        List<TurnoEntidad> listaTurnos = turnoRepositorio.findByEspecialidad(id);
        return listaTurnos.isEmpty() ? Collections.emptyList() : findByEspecialidadOProfesional(listaTurnos);
    }

    @Override
    public List<TurnoPorEspecialidadYProfesionalDTO> findByProfesionalId(Long id) {
        List<TurnoEntidad> listaTurnos = turnoRepositorio.findByProfesional(id);
        return listaTurnos.isEmpty() ? Collections.emptyList() : findByEspecialidadOProfesional(listaTurnos);
    }

    // region Validaciones-filtracion

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
            throw new TurnoFueraDeDiasHabilesClinicaException(
                    "No se puede dar un turno fuera de los dias habiles de la clinica");
        }

        boolean validacionDiaProfesional = profesionalEntidad.getDiasLaborales()
                .stream().anyMatch(dia -> dia.getDia().equals(turnosEntidad.getFechaTurno().getDayOfWeek().name()));

        if (!validacionDiaProfesional) {
            throw new TurnoFueraDeDiasHabilesProfesionalException(
                    "No se puede dar un turno fuera de los dias laborales del profesional");
        }

        boolean validacionHora = profesionalEntidad.getInicioJornada().isBefore(turnosEntidad.getHoraTurno())
                || profesionalEntidad.getInicioJornada().equals(turnosEntidad.getHoraTurno())
                        && profesionalEntidad.getFinalizacionJornada()
                                .isAfter(turnosEntidad.getHoraTurno());

        if (!validacionHora) {
            throw new TurnoFueraDelHorarioLaboralProfesionalException(
                    "No se puede dar un turno fuera del horario laboral del profesional");
        }

        boolean validarHoraTurno = profesionalEntidad.getHorariosProfesional().stream()
                .anyMatch(horario -> horario.getInicioTurno().equals(turnosEntidad.getHoraTurno()));

        if (!validarHoraTurno) {
            throw new TurnoFueraDeLosHorariosEstablecidosProfesionalException(
                    "No se puede dar un turno que no coincida con los horarios establecidos");
        }
        return validacionDia && validacionDiaProfesional && validacionHora && validarHoraTurno;
    }

    private Boolean ValidarDisponibilidadTurno(TurnoEntidad turnoEntidad) {

        List<TurnoEntidad> listaTurnos = (List<TurnoEntidad>) turnoRepositorio.findAll();

        boolean validacionDisponibilidad = listaTurnos.stream()
                .anyMatch(turno -> turno.getFechaTurno().equals(turnoEntidad.getFechaTurno())
                        && turno.getHoraTurno().equals(turnoEntidad.getHoraTurno())
                        && !turno.getId().equals(turnoEntidad.getId()));

        if (validacionDisponibilidad) {
            throw new TurnoYaExistenteException("El turno solicitado ya fue dado para esa fecha y hora");
        }

        return true;
    }

    private Boolean ValidarHorarioTurno(Long id) {
        TurnoEntidad turnoEntidad = turnoRepositorio.findById(id).orElse(null);
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime fechaHoraTurno = LocalDateTime.of(turnoEntidad.getFechaTurno(), turnoEntidad.getHoraTurno());
        // Convierto la hora y fecha del turno en uno solo para comparar correctamente
        // la condicion
        if (localDateTime.isBefore(fechaHoraTurno.minusHours(1)) || localDateTime.isAfter(fechaHoraTurno)) {
            return true;
        }
        return false;
    }

    private List<TurnoPorEspecialidadYProfesionalDTO> findByEspecialidadOProfesional(List<TurnoEntidad> listaTurnos) {
        List<TurnoPorEspecialidadYProfesionalDTO> listaPacienteTurnoDTO = listaTurnos.stream().map(turno -> {
            TurnoPorEspecialidadYProfesionalDTO especialidadYProfesionalDTO = new TurnoPorEspecialidadYProfesionalDTO();
            especialidadYProfesionalDTO
                    .setNombreEspecialidad(turno.getProfesionalEntidad().getEspecialidad().getNombre());
            especialidadYProfesionalDTO.setNombreCompletoProfesional(
                    turno.getProfesionalEntidad().getNombre() + " " + turno.getProfesionalEntidad().getApellido());
            especialidadYProfesionalDTO.setNombreCompletoPaciente(
                    turno.getPacienteEntidad().getNombre() + " " + turno.getPacienteEntidad().getApellido());
            especialidadYProfesionalDTO.setFechaTurno(turno.getFechaTurno());
            especialidadYProfesionalDTO.setHoraTurno(turno.getHoraTurno());
            especialidadYProfesionalDTO.setEstadoTurno(turno.getEstadoTurno());
            return especialidadYProfesionalDTO;
        }).toList();
        return listaPacienteTurnoDTO;
    }

    // endregion

}
