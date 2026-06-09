package com.santyman.hospital.service.interfaces;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.santyman.hospital.dtos.request.EmpleadoRequestDto;
import com.santyman.hospital.dtos.response.EmpleadoResponseDto;
import com.santyman.hospital.model.EstadoPersona;

public interface EmpleadosService {

	EmpleadoResponseDto crearEmpleado(EmpleadoRequestDto dto);

	Page<EmpleadoResponseDto> listarEmpleados(Pageable pageable);

	Page<EmpleadoResponseDto> listarPorEstado(EstadoPersona estado, Pageable page);

	void eliminarEmpleado(Long id);

	Optional<EmpleadoResponseDto> buscarPorId(Long id);
}
