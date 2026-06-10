package com.santyman.hospital.dtos.response;

import java.time.LocalDateTime;

import com.santyman.hospital.model.EstadoPersona;
import lombok.Data;

@Data
public class AtencionResponseDto {
	private Long id;
	private LocalDateTime fecha;
	private String motivo;
	private PacienteResponseDto paciente;
	private EmpleadoResponseDto empleado;
	private EstadoPersona estado;
}
