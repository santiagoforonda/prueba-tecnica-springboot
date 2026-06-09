package com.santyman.hospital.dtos.response;

import com.santyman.hospital.model.EstadoPersona;
import lombok.Data;


@Data
public class PersonaResponseDto {
    private Long id;
    private String nombre;
    private String email;
    private EstadoPersona estado;
}
