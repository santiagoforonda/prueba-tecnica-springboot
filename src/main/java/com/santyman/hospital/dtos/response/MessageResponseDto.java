package com.santyman.hospital.dtos.response;

import lombok.Data;


@Data
public class MessageResponseDto {
    

    private String mensaje;

    public MessageResponseDto(String string) {
        this.mensaje=mensaje;
    }
}
