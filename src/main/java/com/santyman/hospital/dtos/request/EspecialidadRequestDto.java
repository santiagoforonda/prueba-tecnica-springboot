package com.santyman.hospital.dtos.request;

import com.santyman.hospital.model.EstadoPersona;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EspecialidadRequestDto {
    
    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;

    @NotNull(message = "El estado es obligatorio")
    private EstadoPersona estado;
}
