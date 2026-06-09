package com.santyman.hospital.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.santyman.hospital.dtos.request.EmpleadoRequestDto;
import com.santyman.hospital.dtos.response.EmpleadoResponseDto;


public interface EmpleadosService {

	EmpleadoResponseDto crearEmpleado(EmpleadoRequestDto dto);

	List<EmpleadoResponseDto> listarEmpleados();

	Page<EmpleadoResponseDto> listarPorEstado(String estado, Pageable page);

	void eliminarEmpleado(Long id);

	Optional<EmpleadoResponseDto> buscarPorId(Long id);

	EmpleadoResponseDto actualizarEmpleado(Long id, EmpleadoRequestDto dto);
}
