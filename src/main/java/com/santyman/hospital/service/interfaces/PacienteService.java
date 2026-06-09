package com.santyman.hospital.service.interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.santyman.hospital.dtos.request.PacienteRequestDto;
import com.santyman.hospital.dtos.response.PacienteResponseDto;
import com.santyman.hospital.model.EstadoPersona;

public interface PacienteService {

	PacienteResponseDto crearPaciente(PacienteRequestDto dto);

	PacienteResponseDto obtenerPorId(Long id);

	PacienteResponseDto actualizarPaciente(Long id, PacienteRequestDto dto);

	void eliminarPaciente(Long id);

	Page<PacienteResponseDto> listarPacientes(Pageable pageable);

    Page<PacienteResponseDto> listarPacientesPorEstado(EstadoPersona estado,Pageable pageable);

	Page<PacienteResponseDto> listarTodosLosActivos(Pageable pageable);
}
