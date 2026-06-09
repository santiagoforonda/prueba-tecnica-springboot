package com.santyman.hospital.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santyman.hospital.dtos.request.EmpleadoRequestDto;
import com.santyman.hospital.dtos.response.EmpleadoResponseDto;
import com.santyman.hospital.exceptions.BusinesException;
import com.santyman.hospital.exceptions.InvalidRequestException;
import com.santyman.hospital.exceptions.ResourceNotFoundException;
import com.santyman.hospital.mapper.EmpleadoMapper;
import com.santyman.hospital.model.Empleado;
import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.model.Persona;
import com.santyman.hospital.repository.IEmpleadoRepository;
import com.santyman.hospital.repository.IPersonaRepository;
import com.santyman.hospital.service.interfaces.EmpleadosService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmpleadosServiceImpl implements EmpleadosService {

	private final IEmpleadoRepository empleadoRepository;
	private final IPersonaRepository personaRepository;
	private final EmpleadoMapper empleadoMapper;

	@Override
	@Transactional
	public EmpleadoResponseDto crearEmpleado(EmpleadoRequestDto dto) {
		if (dto.getPersonaId() == null) {
			throw new InvalidRequestException("La persona es obligatoria");
		}

		Persona persona = personaRepository.findById(dto.getPersonaId())
				.orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ese ID " + dto.getPersonaId()));

		if (empleadoRepository.findByPersona(persona).isPresent()) {
			throw new BusinesException("La persona ya tiene un empleado asociado");
		}

		Empleado empleado = empleadoMapper.toEntity(dto);
		empleado.setPersona(persona);
		empleado = empleadoRepository.save(empleado);
		log.info("Empleado creado. id={}", empleado.getId());

		return empleadoMapper.toResponse(empleado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmpleadoResponseDto> listarEmpleados() {
		return empleadoRepository.findAll().stream().map(empleadoMapper::toResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EmpleadoResponseDto> listarPorEstado(String estado, Pageable pageable) {
        Page<Empleado> page = empleadoRepository.findByEstado(Enum.valueOf(EstadoPersona.class,estado.toUpperCase()), pageable);
        return page.map(empleadoMapper::toResponse);   
	}

	@Override
	@Transactional
	public void eliminarEmpleado(Long id) {
		if (!empleadoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Empleado no encontrado con ese ID " + id);
		}

		empleadoRepository.deleteById(id);
		log.info("Empleado eliminado. id={}", id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<EmpleadoResponseDto> buscarPorId(Long id) {
		return empleadoRepository.findById(id).map(empleadoMapper::toResponse);
	}

    @Override
    @Transactional
    public EmpleadoResponseDto actualizarEmpleado(Long id, EmpleadoRequestDto dto) {
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Empleado no encontrado"));

        if(!empleado.getPersona().getId().equals(dto.getPersonaId())){
            Persona persona = personaRepository.findById(dto.getPersonaId()).orElseThrow(()-> new ResourceNotFoundException("La persona no fue encontrada"));
            empleado.setPersona(persona);  
        }

        
        empleadoMapper.updateEntity(empleado, dto);

        Empleado updateEmpleado = empleadoRepository.save(empleado);
        return empleadoMapper.toResponse(updateEmpleado);
    }
}
