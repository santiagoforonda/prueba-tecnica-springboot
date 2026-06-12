package com.santyman.hospital.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santyman.hospital.dtos.request.AuthLoginRequestDto;
import com.santyman.hospital.dtos.request.AuthRegisterRequestDto;
import com.santyman.hospital.dtos.response.AuthResponseDto;
import com.santyman.hospital.dtos.response.MessageResponseDto;
import com.santyman.hospital.service.interfaces.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<MessageResponseDto> retgister(@Valid @RequestBody AuthRegisterRequestDto requestDto){
        MessageResponseDto response = authService.registrar(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthLoginRequestDto requestDto){
        AuthResponseDto response = authService.login(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
