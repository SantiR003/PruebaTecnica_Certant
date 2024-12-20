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

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.PacienteEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces.IPacienteServicio;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/paciente")
public class PacienteControlador {

    @Autowired
    IPacienteServicio iPacienteServicio;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<PacienteEntidad> listaPacientes = iPacienteServicio.findAll();
            return ResponseEntity.ok(listaPacientes);
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        } catch (Exception e) { /* devolver lista vacia */
            String mensajeError = "Error al acceder a los pacientes: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid PacienteEntidad pacienteEntidad) {
        try {
            iPacienteServicio.save(pacienteEntidad);
            return ResponseEntity.ok("Paciente guardado con exito");
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        } catch (ConstraintViolationException e) {
            String mensajeError = "Datos incorrectos: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensajeError);
        } catch (Exception e) {
            String mensajeError = "Error al acceder a los pacientes: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid PacienteEntidad pacienteEntidad, @PathVariable Long id) {
        try {
            iPacienteServicio.update(pacienteEntidad, id);
            return ResponseEntity.ok("Paciente actualizado con exito");
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        } catch (ConstraintViolationException e) {
            String mensajeError = "Datos incorrectos: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensajeError);
        } catch (Exception e) {
            String mensajeError = "Error al acceder a los pacientes: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> update(@PathVariable Long id) {
        try {
            iPacienteServicio.delete(id);
            return ResponseEntity.ok("Paciente eliminado con exito");
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        } catch (ConstraintViolationException e) {
            String mensajeError = "Datos incorrectos: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensajeError);
        } catch (Exception e) {
            String mensajeError = "Error al acceder a los pacientes: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
        }
    }

}
