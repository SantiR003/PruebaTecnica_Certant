package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "horario_profesional")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Valid
@Builder
public class HorarioProfesional {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "inicio_turno", nullable = false)
    @NotNull
    private LocalTime inicioTurno;

    @ManyToOne(targetEntity = ProfesionalEntidad.class)
    @JoinColumn(name = "id_profesional", nullable = false)
    @JsonManagedReference
    ProfesionalEntidad profesionaleEntidad;

}
