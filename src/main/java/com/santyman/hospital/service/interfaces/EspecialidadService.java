package com.santyman.hospital.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.santyman.hospital.dtos.request.EspecialidadRequestDto;
import com.santyman.hospital.dtos.response.EspecialidadResponseDto;
import com.santyman.hospital.model.EstadoPersona;

public interface EspecialidadService {

	EspecialidadResponseDto crearEspecialidad(EspecialidadRequestDto dto);

	EspecialidadResponseDto actualizarEspecialidad(Long id, EspecialidadRequestDto dto);

	EspecialidadResponseDto obtenerPorId(Long id);

    Page<EspecialidadResponseDto> listarEspecialidades(Pageable pageable);

	Page<EspecialidadResponseDto> buscarPorNombre(String nombre, Pageable pageable);

	Page<EspecialidadResponseDto> listarPorEstado(EstadoPersona estado, Pageable pageable);
}
