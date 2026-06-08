package com.santyman.hospital.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atenciones")
@Getter
@Setter
@Builder
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotNull(message = "El estado de la atención es obligatorio")
    @Enumerated(EnumType.STRING)
    private EstadoPersona estado;

    @NotNull(message = "El paciente es obligatorio")
    @OneToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "FK_atencion_paciente"))
    private Paciente paciente;

    @NotNull(message = "El empleado es obligatorio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id", nullable = false, foreignKey = @ForeignKey(name = "FK_atencion_empleado"))
    private Empleado empleado;
}
