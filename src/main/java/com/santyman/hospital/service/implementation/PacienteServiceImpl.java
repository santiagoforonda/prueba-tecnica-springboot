package com.santyman.hospital.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santyman.hospital.dtos.request.PacienteRequestDto;
import com.santyman.hospital.dtos.response.PacienteResponseDto;
import com.santyman.hospital.exceptions.BusinesException;
import com.santyman.hospital.exceptions.InvalidRequestException;
import com.santyman.hospital.exceptions.ResourceNotFoundException;
import com.santyman.hospital.mapper.PacienteMapper;
import com.santyman.hospital.model.Paciente;
import com.santyman.hospital.model.Persona;
import com.santyman.hospital.repository.IPacienteRepository;
import com.santyman.hospital.repository.IPersonaRepository;
import com.santyman.hospital.service.interfaces.PacienteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PacienteServiceImpl implements PacienteService {

	private final IPacienteRepository pacienteRepository;
	private final IPersonaRepository personaRepository;
	private final PacienteMapper pacienteMapper;

	@Override
	@Transactional
	public PacienteResponseDto crearPaciente(PacienteRequestDto dto) {
		if (dto.getPersonaId() == null) {
			throw new InvalidRequestException("La persona es obligatoria");
		}

		Persona persona = personaRepository.findById(dto.getPersonaId())
				.orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ese ID " + dto.getPersonaId()));

		if (pacienteRepository.existsByPersona(persona)) {
			throw new BusinesException("La persona ya tiene un paciente asociado");
		}

		Paciente paciente = pacienteMapper.toEntity(dto);
		paciente.setPersona(persona);
		paciente = pacienteRepository.save(paciente);
		log.info("Paciente creado. id={}", paciente.getId());

		return pacienteMapper.toResponse(paciente);
	}

	@Override
	@Transactional(readOnly = true)
	public PacienteResponseDto obtenerPorId(Long id) {
		return pacienteRepository.findById(id)
				.map(pacienteMapper::toResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ese ID " + id));
	}

	@Override
	@Transactional
	public PacienteResponseDto actualizarPaciente(Long id, PacienteRequestDto dto) {
		Paciente paciente = pacienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ese ID " + id));

		if (dto.getPersonaId() == null) {
			throw new InvalidRequestException("La persona es obligatoria");
		}

		Persona persona = personaRepository.findById(dto.getPersonaId())
				.orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ese ID " + dto.getPersonaId()));

		if (!paciente.getPersona().getId().equals(persona.getId()) && pacienteRepository.existsByPersona(persona)) {
			throw new BusinesException("La persona ya tiene un paciente asociado");
		}

		pacienteMapper.updateEntity(paciente, dto);
		paciente.setPersona(persona);
		paciente = pacienteRepository.save(paciente);
		log.info("Paciente actualizado. id={}", paciente.getId());

		return pacienteMapper.toResponse(paciente);
	}

	@Override
	@Transactional
	public void eliminarPaciente(Long id) {
		if (!pacienteRepository.existsById(id)) {
			throw new ResourceNotFoundException("Paciente no encontrado con ese ID " + id);
		}

		pacienteRepository.deleteById(id);
		log.info("Paciente eliminado. id={}", id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PacienteResponseDto> listarPacientes(Pageable pageable) {
		return pacienteRepository.findAll(pageable).map(pacienteMapper::toResponse);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PacienteResponseDto> listarPacientesPorEstado(com.santyman.hospital.model.EstadoPersona estado,
			Pageable pageable) {
		return pacienteRepository.findByEstado(estado, pageable).map(pacienteMapper::toResponse);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PacienteResponseDto> listarTodosLosActivos(Pageable pageable) {
		return pacienteRepository.findByEstado(com.santyman.hospital.model.EstadoPersona.ACTIVO, pageable)
				.map(pacienteMapper::toResponse);
	}
}
