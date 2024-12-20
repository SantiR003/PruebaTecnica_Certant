package com.pruebatecnica.certant.pruebatecnica_certant.persistencia.entidad;

import java.time.LocalDate;
import java.util.List;

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
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paciente")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Valid
@Builder
public class PacienteEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    @NotNull
    @NotBlank
    private String nombre;
    @Column(length = 20, nullable = false)
    @NotNull
    @NotBlank
    private String apellido;
    @Column(length = 10, nullable = false)
    @NotNull
    @NotBlank
    private String dni;
    @Column(length = 50, nullable = false)
    @NotNull
    @NotBlank
    private String direccion;
    @Column(length = 20, nullable = false)
    @NotNull
    @NotBlank
    private String telefono;
    @Column(nullable = false, name = "fecha_de_nacimiento")
    @NotNull
    private LocalDate fechaNacimiento;
    @Column(nullable = false)
    @Pattern(regexp = "^(?i)(Masculino|femenino)$", message = "Porfavor, ingresar correctamente el genero")
    private String genero;

    @OneToMany(targetEntity = TurnosEntidad.class, mappedBy = "pacienteEntidad")
    List<TurnosEntidad> turnosEntidad;

}
