package com.santyman.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.santyman.hospital.dtos.request.PacienteRequestDto;
import com.santyman.hospital.dtos.response.PacienteResponseDto;
import com.santyman.hospital.mapper.config.IMapperConfig;
import com.santyman.hospital.model.Paciente;

@Mapper(config = IMapperConfig.class, uses = {PersonaMapper.class})
public interface PacienteMapper {

    @Mapping(source = "personaId", target = "persona.id")
    Paciente toEntity(PacienteRequestDto dto);

    PacienteResponseDto toResponse(Paciente entity);

    @Mapping(source = "personaId", target = "persona.id")
    void updateEntity(@MappingTarget Paciente entity, PacienteRequestDto dto);
}
