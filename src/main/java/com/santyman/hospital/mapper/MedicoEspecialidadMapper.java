package com.santyman.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.santyman.hospital.dtos.request.MedicoEspecialidadRequestDto;
import com.santyman.hospital.dtos.response.MedicoEspecialidadResponseDto;
import com.santyman.hospital.mapper.config.IMapperConfig;
import com.santyman.hospital.model.MedicoEspecialidad;

@Mapper(config = IMapperConfig.class, uses = {EmpleadoMapper.class, EspecialidadMapper.class})
public interface MedicoEspecialidadMapper {

	@Mapping(source = "empleadoId", target = "empleado.id")
	@Mapping(source = "especialidadId", target = "especialidad.id")
	MedicoEspecialidad toEntity(MedicoEspecialidadRequestDto dto);

	MedicoEspecialidadResponseDto toResponse(MedicoEspecialidad entity);

	@Mapping(source = "empleadoId", target = "empleado.id")
	@Mapping(source = "especialidadId", target = "especialidad.id")
	void updateEntity(@MappingTarget MedicoEspecialidad entity, MedicoEspecialidadRequestDto dto);
}
