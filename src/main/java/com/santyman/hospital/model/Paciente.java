package com.santyman.hospital.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="pacientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false,foreignKey = @ForeignKey(name="FK_paciente_persona"))
    private Persona persona;

    @Enumerated(EnumType.STRING)
    private Roles rol;


    @Enumerated(EnumType.STRING)
    private EstadoPersona estado;
}
