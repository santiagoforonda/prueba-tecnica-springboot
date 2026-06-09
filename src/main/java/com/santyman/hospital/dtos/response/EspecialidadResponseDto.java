package com.santyman.hospital.dtos.response;

import com.santyman.hospital.model.EstadoPersona;

import lombok.Data;


@Data
public class EspecialidadResponseDto {
    private Long id;
    private String nombre;
    private EstadoPersona estado;
}
