package com.santyman.hospital.model;

import java.time.LocalDateTime;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atenciones")
@Getter
@Setter
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private String motivo;


    @Enumerated(EnumType.STRING)
    private EstadoAtencion estado;


    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false, foreignKey = @ForeignKey(name = "FK_atencion_paciente"))
    private Paciente paciente;

    
    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id", nullable = false, foreignKey = @ForeignKey(name = "FK_atencion_empleado"))
    private Empleado empleado;
}
