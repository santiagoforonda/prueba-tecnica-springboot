package com.santyman.hospital.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthLoginRequestDto {
    
    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    
}
