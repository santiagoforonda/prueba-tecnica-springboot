package com.santyman.hospital.security;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.santyman.hospital.model.Empleado;
import com.santyman.hospital.model.Persona;
import com.santyman.hospital.model.Roles;
import com.santyman.hospital.model.Usuario;
import com.santyman.hospital.repository.IEmpleadoRepository;
import com.santyman.hospital.repository.IPacienteRepository;
import com.santyman.hospital.repository.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    

    private final IUsuarioRepository usuarioRepository;
    private final IPacienteRepository pacienteRepository;
    private final IEmpleadoRepository empleadoRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("El usuario no fue encontrado"));
        Persona persona = usuario.getPersona();

        Roles rol;

        if(pacienteRepository.existsByPersona(persona)){
            rol = Roles.PACIENTE;
        }else{
            Empleado empleado = empleadoRepository.findByPersona(persona).orElseThrow(()-> new UsernameNotFoundException("Empleado no encontrado"));
            rol = empleado.getRol();
        }

        List<SimpleGrantedAuthority> authorityList = Collections.singletonList( new SimpleGrantedAuthority("ROLE_"+rol.name()));

        return new User(usuario.getUsername(),usuario.getPassword(),authorityList);
    }



}
