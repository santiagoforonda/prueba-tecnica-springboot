package com.santyman.hospital.service.interfaces;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.santyman.hospital.dtos.request.AtencionRequestDto;
import com.santyman.hospital.dtos.response.AtencionResponseDto;
import com.santyman.hospital.model.EstadoPersona;

public interface AtencionService {

	AtencionResponseDto crearAtencion(AtencionRequestDto dto);

	AtencionResponseDto obtenerPorId(Long id);

    Page<AtencionResponseDto> listarAtenciones(Pageable apge);

	Page<AtencionResponseDto> listarPorPaciente(Long pacienteId, Pageable pageable);

	Page<AtencionResponseDto> listarPorEmpleado(Long empleadoId, Pageable pageable);

	Page<AtencionResponseDto> listarPorEstado(EstadoPersona estado, Pageable pageable);

	Page<AtencionResponseDto> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);

	Page<AtencionResponseDto> buscarPorMotivo(String motivo, Pageable pageable);

	AtencionResponseDto actualizarAtencion(Long id, AtencionRequestDto dto);

	void eliminarAtencion(Long id);

	Page<AtencionResponseDto> listarAtencionesDelPacienteAutenticado(Pageable pageable);
}
