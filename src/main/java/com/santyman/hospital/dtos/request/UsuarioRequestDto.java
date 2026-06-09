package com.santyman.hospital.dtos.request;

import lombok.Data;

@Data
public class UsuarioRequestDto {
    
    private String username;

    private String password;

    private Long personaId;
}
