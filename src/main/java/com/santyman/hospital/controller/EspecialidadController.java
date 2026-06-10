package com.santyman.hospital.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santyman.hospital.dtos.request.EspecialidadRequestDto;
import com.santyman.hospital.dtos.response.EspecialidadResponseDto;
import com.santyman.hospital.service.interfaces.EspecialidadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @Operation(summary = "Crear una especialidad")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Especialidad creada"),
        @ApiResponse(responseCode = "400", description = "Request invalido")
    })
    @PostMapping
    public ResponseEntity<EspecialidadResponseDto> crearEspecialidad(@Valid @RequestBody EspecialidadRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadService.crearEspecialidad(request));
    }

    @Operation(summary = "Obtener una especialidad por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especialidadService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todas las especialidades")
    @GetMapping("/all")
    public ResponseEntity<Page<EspecialidadResponseDto>> listarEspecialidades(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(especialidadService.listarEspecialidades(pageable));
    }

    @Operation(summary = "Buscar especialidades por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<Page<EspecialidadResponseDto>> buscarPorNombre(@RequestParam String nombre,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(especialidadService.buscarPorNombre(nombre, pageable));
    }

    @Operation(summary = "Listar especialidades por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Page<EspecialidadResponseDto>> listarPorEstado(@PathVariable String estado,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(especialidadService.listarPorEstado(estado, pageable));
    }

    @Operation(summary = "Actualizar una especialidad")
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDto> actualizarEspecialidad(@PathVariable Long id,
            @Valid @RequestBody EspecialidadRequestDto request) {
        return ResponseEntity.ok(especialidadService.actualizarEspecialidad(id, request));
    }
}