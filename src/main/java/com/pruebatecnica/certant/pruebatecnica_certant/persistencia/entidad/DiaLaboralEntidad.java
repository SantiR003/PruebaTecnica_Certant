package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dia_laboral")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Valid
public class DiaLaboralEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dias_de_la_semana", updatable = false, nullable = false)
    @Pattern(regexp = "\b(?i)(Lunes|Martes|Miércoles|Jueves|Viernes|Sábado|Domingo)\b", message = "Porfavor, ingresar correctamente el dia")
    private String dia;

}
