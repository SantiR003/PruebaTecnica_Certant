package com.pruebatecnica.certant.pruebatecnica_certant.servicio.implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad.PacienteEntidad;
import com.pruebatecnica.certant.pruebatecnica_certant.persistencia.repositorio.PacienteRepositorio;
import com.pruebatecnica.certant.pruebatecnica_certant.servicio.interfaces.IPacienteServicio;

@Service
public class PacienteServicioImpl implements IPacienteServicio {

    @Autowired
    PacienteRepositorio pacienteRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<PacienteEntidad> findAll() {
        return (List<PacienteEntidad>) pacienteRepositorio.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteEntidad findById(Long id) {
        return pacienteRepositorio.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteEntidad findByDni(String dni) {
        return pacienteRepositorio.findByDni(dni).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(PacienteEntidad pacienteEntidad) {
        pacienteRepositorio.save(pacienteEntidad);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PacienteEntidad pacienteEntidad, Long id) {

        try {
            PacienteEntidad pacienteDesactualizado = pacienteRepositorio.findById(id).orElse(null);

            if (pacienteDesactualizado == null) {
                throw new RuntimeException("Paciente no encontrado: " + id);
            }
            pacienteDesactualizado.setNombre(pacienteEntidad.getNombre());
            pacienteDesactualizado.setApellido(pacienteEntidad.getApellido());
            pacienteDesactualizado.setDni(pacienteEntidad.getDni());
            pacienteDesactualizado.setDireccion(pacienteEntidad.getDireccion());
            pacienteDesactualizado.setTelefono(pacienteEntidad.getTelefono());
            pacienteDesactualizado.setFechaNacimiento(pacienteEntidad.getFechaNacimiento());
            pacienteRepositorio.save(pacienteDesactualizado);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al actualizar el paciente: " + id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // Se revierte en caso de cualquier tipo de fallo
    public void delete(Long id) {

        try {
            PacienteEntidad pacienteAEliminar = pacienteRepositorio.findById(id).orElse(null);

            if (pacienteAEliminar == null) {
                throw new RuntimeException("Paciente no encontrado: " + id);
            }
            pacienteRepositorio.delete(pacienteAEliminar);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al eliminar el paciente: " + id);
        }

    }

}
