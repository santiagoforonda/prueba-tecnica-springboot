package com.santyman.hospital.service.implementation;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.santyman.hospital.dtos.request.AuthLoginRequestDto;
import com.santyman.hospital.dtos.request.AuthRegisterRequestDto;
import com.santyman.hospital.dtos.response.AuthResponseDto;
import com.santyman.hospital.dtos.response.MessageResponseDto;
import com.santyman.hospital.exceptions.ResourceNotFoundException;
import com.santyman.hospital.model.Empleado;
import com.santyman.hospital.model.Persona;
import com.santyman.hospital.model.Roles;
import com.santyman.hospital.model.Usuario;
import com.santyman.hospital.repository.IEmpleadoRepository;
import com.santyman.hospital.repository.IPacienteRepository;
import com.santyman.hospital.repository.IPersonaRepository;
import com.santyman.hospital.repository.IUsuarioRepository;
import com.santyman.hospital.security.JwtUtil;
import com.santyman.hospital.service.interfaces.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    
    private final IUsuarioRepository usuarioRepository;
    private final IPersonaRepository personaRepository;
    private final IPacienteRepository pacienteRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public MessageResponseDto registrar(AuthRegisterRequestDto request) {
        if (usuarioRepository.existsByUsername(request.getUsuario())) {
            throw new IllegalArgumentException("El usuario ya existe: " + request.getUsuario());
        }

        Persona persona = personaRepository.findById(request.getPersonaId())
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con id: " + request.getPersonaId()));

        Usuario nuevoUsuario = Usuario.builder()
                .username(request.getUsuario())
                .password(passwordEncoder.encode(request.getContrasena()))
                .persona(persona)
                .build();

        usuarioRepository.save(nuevoUsuario);
        

        return new MessageResponseDto("Usuario registrado exitosamente: " + request.getUsuario());
    }

    @Override
    public AuthResponseDto login(AuthLoginRequestDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsuario(),
                            request.getPassword()
                    )
            );

            if(!authentication.isAuthenticated()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }


        Usuario usuario = usuarioRepository.findByUsername(request.getUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + request.getUsuario()));

        Persona persona = usuario.getPersona();

        Roles rol;
        if (pacienteRepository.existsByPersona(persona)) {
            rol = Roles.PACIENTE;
        } else {
            Empleado empleado = empleadoRepository.findByPersona(persona)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Empleado no encontrado"));
            rol = empleado.getRol();
        }

        String token = jwtUtil.generateToken(request.getUsuario(), rol);

        return new AuthResponseDto(token, "Bearer");

    }
    
}
