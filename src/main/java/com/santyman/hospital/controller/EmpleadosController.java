package com.santyman.hospital.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.santyman.hospital.dtos.request.EmpleadoRequestDto;
import com.santyman.hospital.dtos.response.EmpleadoResponseDto;
import com.santyman.hospital.service.interfaces.EmpleadosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadosController {

    private final EmpleadosService empleadosService;

    @Operation(summary = "Crear un empleado")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Empleado creado"),
        @ApiResponse(responseCode = "400", description = "Request invalido"),
        @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @PostMapping
    public ResponseEntity<EmpleadoResponseDto> crearEmpleado(@Valid @RequestBody EmpleadoRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadosService.crearEmpleado(request));
    }

    @Operation(summary = "Listar todos los empleados")
    @GetMapping("/all")
    public ResponseEntity<List<EmpleadoResponseDto>> listarEmpleados() {
        return ResponseEntity.ok(empleadosService.listarEmpleados());
    }

    @Operation(summary = "Obtener un empleado por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> buscarPorId(@PathVariable Long id) {
        return empleadosService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar empleados por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Page<EmpleadoResponseDto>> listarPorEstado(@PathVariable String estado,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(empleadosService.listarPorEstado(estado, pageable));
    }

    @Operation(summary = "Actualizar un empleado")
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDto> actualizarEmpleado(@PathVariable Long id,
            @Valid @RequestBody EmpleadoRequestDto request) {
        return ResponseEntity.ok(empleadosService.actualizarEmpleado(id, request));
    }

    @Operation(summary = "Eliminar un empleado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadosService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}