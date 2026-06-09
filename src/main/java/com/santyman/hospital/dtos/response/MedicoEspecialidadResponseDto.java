package com.santyman.hospital.dtos.response;

import lombok.Data;


@Data
public class MedicoEspecialidadResponseDto {
    private Long id;
    private EmpleadoResponseDto empleado;
    private EspecialidadResponseDto especialidad;
}
