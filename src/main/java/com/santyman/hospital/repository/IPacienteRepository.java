package com.santyman.hospital.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.model.Paciente;
import com.santyman.hospital.model.Persona;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {

    Page<Paciente> findByEstado(EstadoPersona estado, Pageable pageable);

    boolean existsByPersona(Persona persona);

    @Query("SELECT p FROM Paciente AS p JOIN Usuario AS u ON u.persona = p.persona WHERE u.username =:username")
    Optional<Paciente> findByUsuarioUsername(@Param("username") String username);
}