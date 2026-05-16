package com.fullstackev2.empleados.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> manejarValidacion(MethodArgumentNotValidException ex) {
        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("[GlobalExceptionHandler] Validacion fallida: {}", errores);
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarArgumentoInvalido(IllegalArgumentException ex) {
        log.warn("[GlobalExceptionHandler] Argumento invalido: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarGenerico(Exception ex) {
        log.error("[GlobalExceptionHandler] Error inesperado: {}", ex.getMessage());
        return ResponseEntity.internalServerError().body("Error interno: " + ex.getMessage());
    }
}