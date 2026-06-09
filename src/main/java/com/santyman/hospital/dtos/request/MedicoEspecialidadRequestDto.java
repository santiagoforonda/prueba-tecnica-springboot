package com.santyman.hospital.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicoEspecialidadRequestDto {
    
    @NotNull(message = "El empleado es obligatorio")
    private Long empleadoId;

    @NotNull(message = "La especialidad es obligatoria")
    private Long especialidadId;
}
