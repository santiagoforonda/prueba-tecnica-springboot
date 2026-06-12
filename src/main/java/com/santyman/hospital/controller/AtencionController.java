package com.santyman.hospital.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santyman.hospital.dtos.request.AtencionRequestDto;
import com.santyman.hospital.dtos.response.AtencionResponseDto;
import com.santyman.hospital.model.EstadoAtencion;
import com.santyman.hospital.model.EstadoPersona;
import com.santyman.hospital.service.interfaces.AtencionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/atenciones")
@Slf4j
@RequiredArgsConstructor
public class AtencionController {
    
    private final AtencionService atencionService;

    @Operation(summary = "Crear una nueva atencion medica")
    @ApiResponses({
        @ApiResponse(responseCode = "201",description =  "Atencion creada"),
        @ApiResponse(responseCode = "400",description ="Request invalido"),
        @ApiResponse(responseCode = "404",description ="Paciente y empleado no encontrado")
    })
    @PostMapping()
    public ResponseEntity<AtencionResponseDto> crearAtencion(@Valid @RequestBody AtencionRequestDto request){
        AtencionResponseDto response = atencionService.crearAtencion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener una atencion por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<AtencionResponseDto> obtenerAtencionPorId(@PathVariable Long id){
        AtencionResponseDto response = atencionService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Operation(summary = "Listar todas las atenciones")
    @GetMapping("/all")
    public ResponseEntity<Page<AtencionResponseDto>> listarTodas(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(atencionService.listarAtenciones(pageable));
    }

    @Operation(summary = "Listar atenciones por paciente")
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<Page<AtencionResponseDto>> listarPorPaciente(@PathVariable Long pacienteId,
            @ParameterObject Pageable pageable){
        return ResponseEntity.ok(atencionService.listarPorPaciente(pacienteId, pageable));
    }

    @Operation(summary = "Listar atenciones por empleado")
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<Page<AtencionResponseDto>> listarPorEmpleado(@PathVariable Long empleadoId,
            @ParameterObject Pageable pageable){
        return ResponseEntity.ok(atencionService.listarPorEmpleado(empleadoId, pageable));
    }

    @Operation(summary = "Listar atenciones por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Page<AtencionResponseDto>> listarPorEstado(@PathVariable EstadoAtencion estado,
            @ParameterObject Pageable pageable){
        return ResponseEntity.ok(atencionService.listarPorEstado(estado, pageable));
    }

    @Operation(summary = "Listar atenciones por rango de fechas")
    @GetMapping("/rango-fechas")
    public ResponseEntity<Page<AtencionResponseDto>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @ParameterObject Pageable pageable){
        return ResponseEntity.ok(atencionService.listarPorRangoFechas(fechaInicio, fechaFin, pageable));
    }

    @Operation(summary = "Buscar atenciones por motivo")
    @GetMapping("/buscar")
    public ResponseEntity<Page<AtencionResponseDto>> buscarPorMotivo(@RequestParam String motivo,
            @ParameterObject Pageable pageable){
        return ResponseEntity.ok(atencionService.buscarPorMotivo(motivo, pageable));
    }

    @Operation(summary = "Actualizar una atencion medica")
    @PutMapping("/{id}")
    public ResponseEntity<AtencionResponseDto> actualizarAtencion(@PathVariable Long id,
            @Valid @RequestBody AtencionRequestDto request){
        return ResponseEntity.ok(atencionService.actualizarAtencion(id, request));
    }

    @Operation(summary = "Eliminar una atencion medica")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAtencion(@PathVariable Long id){
        atencionService.eliminarAtencion(id);
        return ResponseEntity.noContent().build();
    }

}
