package com.santyman.hospital.dtos.request;

import com.santyman.hospital.model.Roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequestDto {
    
    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    private Roles rol;

    @NotNull(message = "La persona es obligatoria")
    private Long personaId;

}
