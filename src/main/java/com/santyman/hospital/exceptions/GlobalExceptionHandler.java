package com.santyman.hospital.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleResourceNotFound(ResourceNotFoundException ex){
        log.error("Error: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND,ex.getMessage());
    }


    @ExceptionHandler(BusinesException.class)
    public ResponseEntity<Map<String,Object>> handleBusinessException(BusinesException ex){
        log.error("Error de negocio: {}", ex.getMessage());
        return buildResponse(HttpStatus.CONFLICT,ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidException(MethodArgumentNotValidException ex){
        log.error("Error de validacion: {}", ex.getMessage());

        Map<String,String>  validationErros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> validationErros.put(error.getField(), error.getDefaultMessage()));

        Map<String,Object> body = new HashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation error");
        body.put("message", validationErros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidRequestException(InvalidRequestException ex){
        log.error("Error en la peticion: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGeneralException(Exception ex){
        log.error("Error inesperado", ex.getMessage());
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Ocurrio un error inesperado.Contacte con soporte");
    }   



    private ResponseEntity<Map<String,Object>> buildResponse(HttpStatus status, String message){
        Map<String,Object> body = new HashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }

}
