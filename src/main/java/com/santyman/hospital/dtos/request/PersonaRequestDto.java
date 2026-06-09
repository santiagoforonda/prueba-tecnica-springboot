package com.santyman.hospital.dtos.request;

import com.santyman.hospital.model.EstadoAtencion;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonaRequestDto {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser un email valido")
    private String email;


    @NotNull(message = "El email es obligatorio")
    private EstadoAtencion estado;
    
}
