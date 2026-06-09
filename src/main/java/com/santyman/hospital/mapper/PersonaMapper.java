package com.santyman.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.santyman.hospital.dtos.request.PersonaRequestDto;
import com.santyman.hospital.dtos.response.PersonaResponseDto;
import com.santyman.hospital.mapper.config.IMapperConfig;
import com.santyman.hospital.model.Persona;

@Mapper(config = IMapperConfig.class)
public interface PersonaMapper {

    @Mapping(source = "estado", target = "estado")
    Persona toEntity(PersonaRequestDto dto);

    PersonaResponseDto toResponse(Persona entity);

    @Mapping(source = "estado", target = "estado")
    void updateEntity(@MappingTarget Persona entity,PersonaRequestDto dto);

}
