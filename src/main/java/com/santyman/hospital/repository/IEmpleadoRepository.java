package com.santyman.hospital.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santyman.hospital.model.Empleado;
import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.model.Persona;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {

    Page<Empleado> findByEstado(EstadoPersona estado, Pageable pageable);

    Optional<Empleado> findByPersona(Persona persona);
}