package com.santyman.hospital.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santyman.hospital.dtos.request.EspecialidadRequestDto;
import com.santyman.hospital.dtos.response.EspecialidadResponseDto;
import com.santyman.hospital.exceptions.InvalidRequestException;
import com.santyman.hospital.exceptions.ResourceNotFoundException;
import com.santyman.hospital.mapper.EspecialidadMapper;
import com.santyman.hospital.model.Especialidad;
import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.repository.IEspecialidadRepository;
import com.santyman.hospital.service.interfaces.EspecialidadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class EspecialidadServiceImpl implements EspecialidadService {

	private final IEspecialidadRepository especialidadRepository;
	private final EspecialidadMapper especialidadMapper;

	@Override
	@Transactional
	public EspecialidadResponseDto crearEspecialidad(EspecialidadRequestDto dto) {
		if (dto.getUsuario() == null || dto.getUsuario().trim().isEmpty()) {
			throw new InvalidRequestException("El usuario es obligatorio");
		}

		Especialidad especialidad = especialidadMapper.toEntity(dto);
		especialidad = especialidadRepository.save(especialidad);
		log.info("Especialidad creada. id={}", especialidad.getId());

		return especialidadMapper.toResponse(especialidad);
	}

	@Override
	@Transactional
	public EspecialidadResponseDto actualizarEspecialidad(Long id, EspecialidadRequestDto dto) {
		Especialidad especialidad = especialidadRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con ese ID " + id));

		if (dto.getUsuario() == null || dto.getUsuario().trim().isEmpty()) {
			throw new InvalidRequestException("El usuario es obligatorio");
		}

		especialidadMapper.updateEntity(especialidad, dto);
		especialidad = especialidadRepository.save(especialidad);
		log.info("Especialidad actualizada. id={}", especialidad.getId());

		return especialidadMapper.toResponse(especialidad);
	}

	@Override
	@Transactional(readOnly = true)
	public EspecialidadResponseDto obtenerPorId(Long id) {
		return especialidadRepository.findById(id)
				.map(especialidadMapper::toResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con ese ID " + id));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EspecialidadResponseDto> listarEspecialidades(Pageable pageable) {
		return especialidadRepository.findAll(pageable).map(especialidadMapper::toResponse);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EspecialidadResponseDto> buscarPorNombre(String nombre, Pageable pageable) {
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new InvalidRequestException("El nombre para busqueda no puede estar vacio");
		}

		return especialidadRepository.searchByNombre(nombre, pageable).map(especialidadMapper::toResponse);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EspecialidadResponseDto> listarPorEstado(String estadoStr,
			Pageable pageable) {
        EstadoPersona estado;
		try{
            estado = EstadoPersona.valueOf(estadoStr.toUpperCase());
        }catch(IllegalArgumentException exception){
            throw new IllegalArgumentException("Estado invalido");
        }

        return especialidadRepository.findByEstadoPersona(estado, pageable).map(especialidadMapper::toResponse);
	}
}
