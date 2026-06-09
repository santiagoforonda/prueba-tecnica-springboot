package com.santyman.hospital.dtos.request;

import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.model.Roles;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmpleadoRequestDto {

    @NotNull(message ="El rol es obligatorio")
    private Roles rol;

    @NotNull(message="La persona es obligatoria")
    private Long personaId;

    @NotNull(message = "El estado es obligatorio")
    private EstadoPersona estado;
}
