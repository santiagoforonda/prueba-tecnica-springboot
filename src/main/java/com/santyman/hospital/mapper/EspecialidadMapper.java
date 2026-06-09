package com.santyman.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.santyman.hospital.dtos.request.EspecialidadRequestDto;
import com.santyman.hospital.dtos.response.EspecialidadResponseDto;
import com.santyman.hospital.mapper.config.IMapperConfig;
import com.santyman.hospital.model.Especialidad;

@Mapper(config = IMapperConfig.class)
public interface EspecialidadMapper {

	@Mapping(source = "usuario", target = "nombre")
	@Mapping(source = "estado", target = "estadoPersona")
	Especialidad toEntity(EspecialidadRequestDto dto);

	@Mapping(source = "estadoPersona", target = "estado")
	EspecialidadResponseDto toResponse(Especialidad entity);

	@Mapping(source = "usuario", target = "nombre")
	@Mapping(source = "estado", target = "estadoPersona")
	void updateEntity(@MappingTarget Especialidad entity, EspecialidadRequestDto dto);
}
