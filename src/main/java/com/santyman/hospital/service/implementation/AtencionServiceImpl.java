package com.santyman.hospital.service.implementation;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santyman.hospital.dtos.request.AtencionRequestDto;
import com.santyman.hospital.dtos.response.AtencionResponseDto;
import com.santyman.hospital.exceptions.BusinesException;
import com.santyman.hospital.exceptions.InvalidRequestException;
import com.santyman.hospital.exceptions.ResourceNotFoundException;
import com.santyman.hospital.mapper.AtencionMapper;
import com.santyman.hospital.model.Atencion;
import com.santyman.hospital.model.Empleado;
import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.model.Paciente;
import com.santyman.hospital.repository.IAtencionRepository;
import com.santyman.hospital.repository.IEmpleadoRepository;
import com.santyman.hospital.repository.IPacienteRepository;
import com.santyman.hospital.service.interfaces.AtencionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AtencionServiceImpl implements AtencionService {

    private final IAtencionRepository atencionRepository;
    private final IPacienteRepository pacienteRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final AtencionMapper atencionMapper;

    @Override
    @Transactional
    public AtencionResponseDto crearAtencion(AtencionRequestDto dto) {
            if(dto.getFecha() == null){
                throw new InvalidRequestException("La fecha de la atencion es obligatoria");
            }
            
            if(dto.getFecha().isBefore(LocalDateTime.now().minusMinutes(1))){
                throw new InvalidRequestException("La fecha de la atencion no puede ser en el pasado");
            }   

            Paciente paciente = pacienteRepository.findById(dto.getPaciente()).orElseThrow(()-> new ResourceNotFoundException("Paciente no encontrado con ese ID"));

            Empleado empleado = empleadoRepository.findById(dto.getEmpleado()).orElseThrow(()-> new ResourceNotFoundException("Empleado no encontrado con ese ID"));

            Atencion atencion = atencionMapper.toEntity(dto);

            atencion.setPaciente(paciente);
            atencion.setEmpleado(empleado);

            atencion = atencionRepository.save(atencion);
            log.info("Atencion creada");

            return atencionMapper.toResponse(atencion);
    }

    @Override
    @Transactional(readOnly = true)
    public AtencionResponseDto obtenerPorId(Long id) {
        return atencionRepository.findById(id).map(atencionMapper::toResponse).orElseThrow(()-> new ResourceNotFoundException("La atencion no fue encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDto> listarAtenciones(Pageable pageable) {
        return atencionRepository.findAll(pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDto> listarPorPaciente(Long pacienteId, Pageable pageable) {
        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(()-> new ResourceNotFoundException("Paciente no encontrado con ese ID " + pacienteId));
        return atencionRepository.findByPaciente(paciente, pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDto> listarPorEmpleado(Long empleadoId, Pageable pageable) {
        Empleado empleado = empleadoRepository.findById(empleadoId).orElseThrow(()-> new ResourceNotFoundException("Empleado no encontrado con ese ID"));
        return atencionRepository.findByEmpleado(empleado, pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDto> listarPorEstado(EstadoPersona estado, Pageable pageable) {
        return atencionRepository.findByEstado(estado, pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDto> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin,
            Pageable pageable) {
        
                if(fechaInicio == null || fechaFin==null){
                    throw new InvalidRequestException("Debe proporcionar fechas validas");
                }

                if(fechaFin.isBefore(fechaInicio)){
                    throw new InvalidRequestException("Rango de fechas invalido");
                }
                
        return atencionRepository.findByFecha(fechaInicio, fechaFin, pageable).map(atencionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDto> buscarPorMotivo(String motivo, Pageable pageable) {
        if(motivo== null || motivo.trim().isEmpty()){
            throw new InvalidRequestException("El motivo para busqueda no puede estar vacio");
        }

        return atencionRepository.searchByMotivo(motivo, pageable).map(atencionMapper::toResponse);
    }

    @Override
    public AtencionResponseDto actualizarAtencion(Long id, AtencionRequestDto dto) {
        Atencion atencion = atencionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Atencion no encontrada"));

        if(atencion.getEstado().equals(EstadoPersona.FINALIZADO)){
            throw new BusinesException("No se puede actualizar una atencion que ya fue finalizada");
        }

        if(dto.getFecha() == null){
            throw new InvalidRequestException("La fecha de la atencion es obligatoria");
        }

        atencion.setFecha(dto.getFecha());
        atencion.setMotivo(dto.getMotivo());
        atencion.setEstado(dto.getEstado());

        if(dto.getPaciente() != null && !dto.getEmpleado().equals(atencion.getPaciente().getId())){
            atencion.setPaciente(pacienteRepository.findById(dto.getPaciente())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + dto.getPaciente())));
        }

        if(dto.getEmpleado() != null && dto.getEmpleado().equals(atencion.getEmpleado().getId())){
            atencion.setEmpleado(empleadoRepository.findById(dto.getEmpleado())
                    .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + dto.getEmpleado())));
        }

        atencion = atencionRepository.save(atencion);
        log.info("Atencion actualizada. id ={}", atencion.getId());
        return atencionMapper.toResponse(atencion);
    }

    @Override
    @Transactional
    public void eliminarAtencion(Long id) {
        if(!atencionRepository.existsById(id)){
            throw new ResourceNotFoundException("Atencion no encontrada");
        }
        atencionRepository.deleteById(id);
        log.info("La atencion fue eliminada");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtencionResponseDto> listarAtencionesDelPacienteAutenticado(String username,Pageable pageable) {
        Paciente paciente = pacienteRepository.findByUsuarioUsername(username).orElseThrow(()-> new IllegalArgumentException("Paciente no encontrado para el usuario autenticado"));

        return atencionRepository.findByPaciente(paciente, pageable).map(atencionMapper::toResponse);
    }
    
}
