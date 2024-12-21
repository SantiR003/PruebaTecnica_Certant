package com.pruebatecnica.certant.pruebatecnica_certant.presentacion.controlador;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.TurnoEntidad;
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

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
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

    @GetMapping("/historial/{id}")
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

    @GetMapping("/historial/{idEspecialidad}/{idProfesional}")
    public ResponseEntity<?> findHistorialEspecialidadYProfesional(@PathVariable Long idEspecialidad,
            @PathVariable Long idProfesional) {
        try {
            List<TurnoPorEspecialidadYProfesionalDTO> listaHistorial = iTurnoServicio
                    .findByEspecialidadAndProfesionalId(idEspecialidad, idProfesional);
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

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid TurnoEntidad turnosEntidad) {
        try {
            iTurnoServicio.save(turnosEntidad);
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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid TurnoEntidad turnosEntidad, @PathVariable Long id) {
        try {
            iTurnoServicio.update(turnosEntidad, id);
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> update(@PathVariable Long id) {
        try {
            iTurnoServicio.delete(id);
            return ResponseEntity.ok("Turno eliminado con exito");
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
