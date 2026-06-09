package com.santyman.hospital.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.santyman.hospital.model.Persona;
import com.santyman.hospital.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String usuario);

    Optional<Persona> findByPersona(Persona persona);

    boolean existsByUsername(String usuario);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :usuario,'%'))")
    Page<Persona> searchByUsuario(@Param("usuario") String usuario, Pageable pageable);

    
}