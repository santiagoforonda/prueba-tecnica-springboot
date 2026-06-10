package com.santyman.hospital.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santyman.hospital.dtos.request.PacienteRequestDto;
import com.santyman.hospital.dtos.response.PacienteResponseDto;
import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.service.interfaces.PacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @Operation(summary = "Crear un paciente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Paciente creado"),
        @ApiResponse(responseCode = "400", description = "Request invalido"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @PostMapping
    public ResponseEntity<PacienteResponseDto> crearPaciente(@Valid @RequestBody PacienteRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.crearPaciente(request));
    }

    @Operation(summary = "Obtener un paciente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDto> obtenerPacientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todos los pacientes")
    @GetMapping("/all")
    public ResponseEntity<Page<PacienteResponseDto>> listarPacientes(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(pacienteService.listarPacientes(pageable));
    }

    @Operation(summary = "Listar pacientes por estado")
    @GetMapping("/estado")
    public ResponseEntity<Page<PacienteResponseDto>> listarPorEstado(@RequestParam EstadoPersona estado,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(pacienteService.listarPacientesPorEstado(estado, pageable));
    }

    @Operation(summary = "Listar pacientes activos")
    @GetMapping("/activos")
    public ResponseEntity<Page<PacienteResponseDto>> listarActivos(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(pacienteService.listarTodosLosActivos(pageable));
    }

    @Operation(summary = "Actualizar un paciente")
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDto> actualizarPaciente(@PathVariable Long id,
            @Valid @RequestBody PacienteRequestDto request) {
        return ResponseEntity.ok(pacienteService.actualizarPaciente(id, request));
    }

    @Operation(summary = "Eliminar un paciente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}