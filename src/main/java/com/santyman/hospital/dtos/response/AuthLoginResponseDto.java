package com.santyman.hospital.dtos.response;

import lombok.Data;

@Data
public class AuthLoginResponseDto {
    
    private String message;

    private String token;

    private String typeToken;

    public AuthLoginResponseDto(String mensaje){
        this.message=mensaje;
    }

    public AuthLoginResponseDto(String token,String typeToken){
        this.token=token;
        this.typeToken=typeToken;
        this.message="Login success";
    }
}
