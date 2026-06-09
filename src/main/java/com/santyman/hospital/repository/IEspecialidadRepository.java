package com.santyman.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.santyman.hospital.model.Especialidad;
import com.santyman.hospital.model.EstadoPersona;

public interface IEspecialidadRepository extends JpaRepository<Especialidad, Long> {

    Page<Especialidad> findByEstadoPersona(EstadoPersona estadoPersona, Pageable pageable);

    @Query("SELECT e FROM Especialidad AS e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Especialidad> searchByNombre(@Param("nombre") String nombre, Pageable pageable);
}