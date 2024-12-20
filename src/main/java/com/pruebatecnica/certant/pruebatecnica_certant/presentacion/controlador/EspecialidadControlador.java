package com.pruebatecnica.certant.pruebatecnica_certant.presentacion.controlador;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces.IEspecilidadServicio;

@RestController
@RequestMapping("/especialidad")
public class EspecialidadControlador {

    @Autowired
    IEspecilidadServicio iEspecilidadServicio;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<String> listaEspecialidades = iEspecilidadServicio.findAll();
            return ResponseEntity.ok(listaEspecialidades);
        } catch (DataAccessException e) {
            String mensajeError = "Error al intentar acceder a los datos: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        } catch (Exception e) { /* devolver lista vacia */
            String mensajeError = "Error al acceder a las especialidades: " + e.getMessage();
            System.err.println(mensajeError);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

}
