package com.santyman.hospital.dtos.request;

import java.time.LocalDateTime;

import com.santyman.hospital.model.EstadoAtencion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AtencionRequestDto {
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotNull(message = "El estado de la atención es obligatorio")
    @Enumerated(EnumType.STRING)
    private EstadoAtencion estado;

    @NotNull(message = "El paciente es obligatorio")
    private Long paciente;

    @NotNull(message = "El empleado es obligatorio")
    private Long empleado;
}
