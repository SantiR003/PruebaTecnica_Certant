package com.pruebatecnica.certant.pruebatecnica_certant.servicio.implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.ProfesionalEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio.ProfesionalRepositorio;
import com.pruebatecnica.certant.pruebatecnica_certant.presentacion.dto.ProfesionalDTO;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces.IProfesionalServicio;

@Service
public class ProfesionalServicioImpl implements IProfesionalServicio {

    @Autowired
    ProfesionalRepositorio profesionalRepositorio;

    @Override
    public List<ProfesionalDTO> findAll() {

        List<ProfesionalEntidad> listaProfesionales = (List<ProfesionalEntidad>) profesionalRepositorio.findAll();
        List<ProfesionalDTO> listaProfesionalesDTO = listaProfesionales.stream().map(profesional -> {
            ProfesionalDTO profesionalDTO = new ProfesionalDTO();
            profesionalDTO.setNombre(profesional.getNombre());
            profesionalDTO.setApellido(profesional.getApellido());
            profesionalDTO.setInicioJornada(profesional.getInicioJornada());
            profesionalDTO.setFinalizacionJornada(profesional.getFinalizacionJornada());
            profesionalDTO.setEspecialidad(profesional.getEspecialidad().getNombre());
            return profesionalDTO;
        }).toList();

        return listaProfesionalesDTO;

    }

    @Override
    public ProfesionalEntidad findById(Long id) {
        return null;
    }

}
