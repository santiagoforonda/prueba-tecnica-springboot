package com.santyman.hospital.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.santyman.hospital.dtos.request.UsuarioRequestDto;
import com.santyman.hospital.dtos.response.UsuarioResponseDto;
import com.santyman.hospital.mapper.config.IMapperConfig;
import com.santyman.hospital.model.Usuario;

@Mapper(config = IMapperConfig.class, uses = {PersonaMapper.class})
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRequestDto dto);

    UsuarioResponseDto toResponse(Usuario entity);

    void updateEntity(@MappingTarget Usuario entity, UsuarioRequestDto dto);
}
