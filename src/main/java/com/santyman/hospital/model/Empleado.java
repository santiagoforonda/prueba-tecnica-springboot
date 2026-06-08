package com.santyman.hospital.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="empleados")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false,foreignKey = @ForeignKey(name="FK_empleado_persona"))
    private Persona persona;


    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    private Roles rol;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    private EstadoPersona estado;

    @OneToMany(mappedBy = "emepleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MedicoEspecialidad> especialidades = new HashSet<>();
}
