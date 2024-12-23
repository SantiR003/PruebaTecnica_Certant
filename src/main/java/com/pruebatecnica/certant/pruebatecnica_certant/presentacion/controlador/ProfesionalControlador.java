package com.pruebatecnica.certant.pruebatecnica_certant.presentacion.controlador;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.PacienteEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.ProfesionalEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.ProfesionalDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces.IProfesionalServicio;

@RestController
@RequestMapping("/profesional")
public class ProfesionalControlador {

    @Autowired
    IProfesionalServicio iProfesionalServicio;

    @GetMapping("/traerTodos")
    public ResponseEntity<?> traerTodos() {
        try {
            List<ProfesionalDTO> listaProfesionales = iProfesionalServicio.findAll();
            return ResponseEntity.ok(listaProfesionales);
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        } catch (Exception e) { /* devolver lista vacia */
            String mensajeError = "Error al acceder al listado de profesionales: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/traerPorId/{id}")
    public ResponseEntity<?> traerPorId(@PathVariable Long id) {
        try {
            ProfesionalEntidad profesional = iProfesionalServicio.findById(id);
            return ResponseEntity.ok(profesional);
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        } catch (Exception e) { /* devolver lista vacia */
            String mensajeError = "Error al acceder al listado de profesionales: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

}
