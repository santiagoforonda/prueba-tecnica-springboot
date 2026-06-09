package com.santyman.hospital.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.santyman.hospital.model.Atencion;
import com.santyman.hospital.model.Empleado;
import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.model.Paciente;

public interface IAtencionRepository extends JpaRepository<Atencion,Long> {

    Page<Empleado> findByEstado(EstadoPersona estado, Pageable pageable);

    Page<Empleado> findByPaciente(Paciente paciente, Pageable pageable);

    Page<Empleado> findByEmpleado(Empleado empleado, Pageable pageable);

    Page<Empleado> findByFecha(LocalDateTime inicio, LocalDateTime fin ,Pageable pageable);
    
    @Query("SELECT a FROM Atencion AS a WHERE LOWER(a.motivo) LIKE LOWER(CONCAT('%', :motivo, '%'))")
    Page<Atencion> searchByMotivo(@Param("motivo") String motivo, Pageable pageable);
}
