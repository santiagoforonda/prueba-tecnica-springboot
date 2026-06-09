package com.santyman.hospital.dtos.response;

import lombok.Data;

@Data
public class UsuarioResponseDto {
    private Long id;
    private String username;
    private PersonaResponseDto persona;
}
