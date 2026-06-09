package com.santyman.hospital.dtos.response;

import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.model.Roles;
import lombok.Data;

@Data
public class EmpleadoResponseDto {
    private Long id;
    private PersonaResponseDto persona;
    private Roles rol;
    private EstadoPersona estado;
}
