package com.santyman.hospital.dtos.request;

import com.santyman.hospital.model.Roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRegisterRequestDto {
    
    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;

    @NotBlank(message = "La clave es obligatoria")
    private String contrasena;

    @NotNull(message = "El rol es obligatorio")
    private Roles rol;

    @NotNull(message = "La persona es obligatoria")
    private Long personaId;
}
