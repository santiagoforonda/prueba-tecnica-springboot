package com.santyman.hospital.service.implementation;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santyman.hospital.dtos.request.AtencionRequestDto;
import com.santyman.hospital.dtos.response.AtencionResponseDto;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPorId'");
    }

    @Override
    public Page<AtencionResponseDto> listarAtenciones(Pageable apge) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarAtenciones'");
    }

    @Override
    public Page<AtencionResponseDto> listarPorPaciente(Long pacienteId, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarPorPaciente'");
    }

    @Override
    public Page<AtencionResponseDto> listarPorEmpleado(Long empleadoId, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarPorEmpleado'");
    }

    @Override
    public Page<AtencionResponseDto> listarPorEstado(EstadoPersona estado, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarPorEstado'");
    }

    @Override
    public Page<AtencionResponseDto> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin,
            Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarPorRangoFechas'");
    }

    @Override
    public Page<AtencionResponseDto> buscarPorMotivo(String motivo, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorMotivo'");
    }

    @Override
    public AtencionResponseDto actualizarAtencion(Long id, AtencionRequestDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarAtencion'");
    }

    @Override
    public void eliminarAtencion(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarAtencion'");
    }

    @Override
    public Page<AtencionResponseDto> listarAtencionesDelPacienteAutenticado(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarAtencionesDelPacienteAutenticado'");
    }
    
}
