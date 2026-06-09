package com.santyman.hospital.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.model.Persona;

public interface IPersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByEmail(String email);

    Page<Persona> findByEstado(EstadoPersona estado, Pageable pageable);

    @Query("SELECT p FROM Persona p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre,'%'))")
    Page<Persona> searchByNombre(@Param("nombre") String nombre, Pageable pageable);
}