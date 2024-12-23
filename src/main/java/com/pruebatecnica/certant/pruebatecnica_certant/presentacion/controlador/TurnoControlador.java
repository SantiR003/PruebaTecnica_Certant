package com.pruebatecnica.certant.pruebatecnica_certant.presentacion.controlador;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebatecnica.certant.pruebatecnica_certant.constante.EstadoTurno;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorEspecialidadYProfesionalDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.TurnoPorPacienteDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces.ITurnoServicio;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/turno")
public class TurnoControlador {

    @Autowired
    ITurnoServicio iTurnoServicio;

    @GetMapping("/traerTodos")
    public ResponseEntity<?> traerTodos() {
        try {
            List<TurnoDTO> listaTurnos = iTurnoServicio.findAll();
            return ResponseEntity.ok(listaTurnos);
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        } catch (Exception e) {
            String mensajeError = "Error al acceder a los turnos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/traerPorId/{id}")
    public ResponseEntity<?> traerPorId(@PathVariable Long id) {
        try {
            TurnoDTO turno = iTurnoServicio.findById(id);
            return ResponseEntity.ok(turno);
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        } catch (Exception e) {
            String mensajeError = "Error al acceder a los turnos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/historial/paciente/{id}")
    public ResponseEntity<?> findHistorialPaciente(@PathVariable Long id) {
        try {
            List<TurnoPorPacienteDTO> listaHistorial = iTurnoServicio.findByPacienteId(id);
            return ResponseEntity.ok(listaHistorial);
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        } catch (Exception e) {
            String mensajeError = "Error al acceder a los turnos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/historial/especialidad/{idEspecialidad}")
    public ResponseEntity<?> findHistorialEspecialidad(@PathVariable Long idEspecialidad) {
        try {
            List<TurnoPorEspecialidadYProfesionalDTO> listaHistorial = iTurnoServicio
                    .findByEspecialidadId(idEspecialidad);
            return ResponseEntity.ok(listaHistorial);
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        } catch (Exception e) { /* devolver lista vacia */
            String mensajeError = "Error al acceder a los turnos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/historial/profesional/{idProfesional}")
    public ResponseEntity<?> findHistorialProfesional(@PathVariable Long idProfesional) {
        try {
            List<TurnoPorEspecialidadYProfesionalDTO> listaHistorial = iTurnoServicio
                    .findByProfesionalId(idProfesional);
            return ResponseEntity.ok(listaHistorial);
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        } catch (Exception e) { /* devolver lista vacia */
            String mensajeError = "Error al acceder a los turnos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody @Valid TurnoDTO turnosDTO) {
        try {
            iTurnoServicio.intermediario(turnosDTO);
            return ResponseEntity.ok("Turno guardado con exito");
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        } catch (ConstraintViolationException e) {
            String mensajeError = "Datos incorrectos: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensajeError);
        } catch (Exception e) {
            String mensajeError = "Error al acceder a los turnos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid TurnoDTO turnosDTO) {
        try {
            iTurnoServicio.update(turnosDTO, id);
            return ResponseEntity.ok("Turno actualizado con exito");
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        } catch (ConstraintViolationException e) {
            String mensajeError = "Datos incorrectos: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensajeError);
        } catch (Exception e) {
            String mensajeError = "Error al acceder a los turnos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }

    @CrossOrigin(origins = { "http://127.0.0.1:5500", "http://localhost:4200" })
    @PatchMapping("/estado/{id}/{estado}")
    public ResponseEntity<?> cambioEstado(@PathVariable Long id, @PathVariable String estado) {
        try {
            EstadoTurno estadoTurno = EstadoTurno.valueOf(estado.toUpperCase());
            iTurnoServicio.cambiarEstadoTurno(id, estadoTurno);
            return ResponseEntity.ok("Turno actualizado con exito");
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        } catch (ConstraintViolationException e) {
            String mensajeError = "Datos incorrectos: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensajeError);
        } catch (Exception e) {
            String mensajeError = "Error al acceder a los turnos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }
}
