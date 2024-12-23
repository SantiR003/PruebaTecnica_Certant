package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especialidad")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Valid
public class EspecialidadEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    @NotBlank
    private String nombre;

    @OneToMany(targetEntity = ProfesionalEntidad.class, mappedBy = "especialidad", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    List<ProfesionalEntidad> profesionales;
}
