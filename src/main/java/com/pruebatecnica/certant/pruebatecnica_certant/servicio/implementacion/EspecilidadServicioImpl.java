package com.pruebatecnica.certant.pruebatecnica_certant.servicio.implementacion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.EspecialidadEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio.EspecialidadRepositorio;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces.IEspecilidadServicio;

@Service
public class EspecilidadServicioImpl implements IEspecilidadServicio {

    @Autowired
    EspecialidadRepositorio especialidadRepositorio;

    @Override
    public List<String> findAll() {

        List<EspecialidadEntidad> listaEspecialidades = (List<EspecialidadEntidad>) especialidadRepositorio.findAll();

        List<String> nombresEspecialidades = listaEspecialidades.stream().map(especialidad -> {
            return especialidad.getNombre();
        }).collect(Collectors.toList());

        return nombresEspecialidades;
    }

    @Override
    public EspecialidadEntidad findById(Long id) {
        return null;
    }

}
