package com.santyman.hospital.dtos.response;

import java.time.LocalDate;

import com.santyman.hospital.model.EstadoAtencion;
import lombok.Data;

@Data
public class AtencionResponseDto {
	private Long id;
	private LocalDate fecha;
	private String motivo;
	private Long pacienteId;
	private Long empleadoId;
	private EstadoAtencion estado;
}
