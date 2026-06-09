package com.santyman.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.santyman.hospital.model.Atencion;

public interface IAtencionRepository extends JpaRepository<Atencion,Long> {
    
    @Query("SELECT a FROM Atencion WHERE LOWER(a.motivo) LIKE LOWER(CONCAT('%', :motivo, '%'))")
    Page<Atencion> searchByMotivo(@Param("motivo") String motivo, Pageable pageable);
}
