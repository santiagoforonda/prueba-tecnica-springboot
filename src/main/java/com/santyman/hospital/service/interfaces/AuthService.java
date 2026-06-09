package com.santyman.hospital.service.interfaces;

import com.santyman.hospital.dtos.request.AuthLoginRequestDto;
import com.santyman.hospital.dtos.request.AuthRegisterRequestDto;
import com.santyman.hospital.dtos.response.AuthResponseDto;
import com.santyman.hospital.dtos.response.MessageResponseDto;

public interface AuthService {
    
    MessageResponseDto registrar(AuthRegisterRequestDto request);

    AuthResponseDto login(AuthLoginRequestDto request);

}
