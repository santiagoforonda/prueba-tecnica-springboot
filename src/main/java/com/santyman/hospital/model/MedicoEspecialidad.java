package com.santyman.hospital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "especialidadesPorMedico")
@Getter
@Setter

public class MedicoEspecialidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false, foreignKey = @ForeignKey(name="fk_medico_especialidad_empleado"))
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "especialidad_id",nullable = false, foreignKey = @ForeignKey(name="fk_medico_especialidad_especialidad"))
    private Especialidad especialidad;

}
