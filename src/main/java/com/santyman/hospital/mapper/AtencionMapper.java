package com.santyman.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.santyman.hospital.dtos.request.AtencionRequestDto;
import com.santyman.hospital.dtos.response.AtencionResponseDto;
import com.santyman.hospital.mapper.config.IMapperConfig;
import com.santyman.hospital.model.Atencion;

@Mapper(config = IMapperConfig.class,uses = {PacienteMapper.class, EmpleadoMapper.class})
public interface AtencionMapper {

    @Mapping(source = "pacienteId", target = "paciente.id")
    @Mapping(source = "empleadoId", target = "empleado.id")
    Atencion toEntity(AtencionRequestDto dto);

    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "empleado.id", target = "empleadoId")
    AtencionResponseDto toResponse(Atencion entity);

    @Mapping(source = "pacienteId", target = "paciente.id")
    @Mapping(source = "empleadoId", target = "empleado.id")
    void updateEntity(@MappingTarget Atencion entity, AtencionRequestDto dto);

}
