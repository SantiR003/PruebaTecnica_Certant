package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profesional")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Valid
public class ProfesionalEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    @NotNull
    @NotBlank
    private String nombre;
    @Column(length = 30, nullable = false)
    @NotNull
    @NotBlank
    private String apellido;
    @Column(name = "inicio_jornada", nullable = false)
    private LocalTime inicioJornada;
    @Column(name = "finalizacion_jornada", nullable = false)
    private LocalTime finalizacionJornada;

    @NotNull
    @Pattern(regexp = "^[123]$", message = "Debe seleccionar uno de los 3 consultorios disponibles")
    private int consultorio;

    @ManyToOne(targetEntity = EspecialidadEntidad.class)
    @JoinColumn(name = "id_especialidad", nullable = false)
    private EspecialidadEntidad especialidad;

    @ManyToMany(targetEntity = DiaLaboralEntidad.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "profesional_dia_laboral", joinColumns = @JoinColumn(name = "profesional_id"), inverseJoinColumns = @JoinColumn(name = "dia_laboral_id"))
    List<DiaLaboralEntidad> diasLaborales;

    @OneToMany(targetEntity = TurnoEntidad.class, mappedBy = "profesionalEntidad")
    List<TurnoEntidad> turnosEntidad;

    @OneToMany(targetEntity = HorarioProfesional.class, mappedBy = "profesionaleEntidad", cascade = CascadeType.ALL, orphanRemoval = true)
    List<HorarioProfesional> horariosProfesional;

}
