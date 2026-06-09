package com.santyman.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.santyman.hospital.dtos.request.EmpleadoRequestDto;
import com.santyman.hospital.dtos.response.EmpleadoResponseDto;
import com.santyman.hospital.mapper.config.IMapperConfig;
import com.santyman.hospital.model.Empleado;

@Mapper(config = IMapperConfig.class, uses = {PersonaMapper.class})
public interface EmpleadoMapper {

	@Mapping(source = "personaId", target = "persona.id")
	Empleado toEntity(EmpleadoRequestDto dto);

	EmpleadoResponseDto toResponse(Empleado entity);

	@Mapping(source = "personaId", target = "persona.id")
	void updateEntity(@MappingTarget Empleado entity, EmpleadoRequestDto dto);
}
