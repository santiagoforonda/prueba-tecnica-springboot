package com.santyman.hospital.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santyman.hospital.model.Empleado;
import com.santyman.hospital.model.Especialidad;
import com.santyman.hospital.model.MedicoEspecialidad;

public interface IMedicoEspecialidadRepository extends JpaRepository<MedicoEspecialidad, Long> {

    Page<MedicoEspecialidad> findByEmpleado(Empleado empleado, Pageable pageable);

    Page<MedicoEspecialidad> findByEspecialidad(Especialidad especialidad, Pageable pageable);
}