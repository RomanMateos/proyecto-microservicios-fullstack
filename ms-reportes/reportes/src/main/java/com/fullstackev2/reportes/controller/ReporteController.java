package com.fullstackev2.reportes.controller;

import com.fullstackev2.reportes.dto.ReporteRequestDTO;
import com.fullstackev2.reportes.dto.ReporteResponseDTO;
import com.fullstackev2.reportes.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// IMPORTS MAGIA HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
@Slf4j
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> listarTodos() {
        log.info("[ReporteController] GET /api/v1/reportes");
        List<ReporteResponseDTO> lista = reporteService.listarTodos();

        lista.forEach(dto ->
                // Suponiendo que el ID en tu DTO se llama reporteId. Cambialo si se llama id.
                dto.add(linkTo(methodOn(ReporteController.class).buscarPorId(dto.getReporteId())).withSelfRel())
        );
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ReporteResponseDTO>> listarActivos() {
        log.info("[ReporteController] GET /api/v1/reportes/activos");
        List<ReporteResponseDTO> lista = reporteService.listarActivos();

        lista.forEach(dto ->
                dto.add(linkTo(methodOn(ReporteController.class).buscarPorId(dto.getReporteId())).withSelfRel())
        );
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[ReporteController] GET /api/v1/reportes/{}", id);
        ReporteResponseDTO dto = reporteService.buscarPorId(id);

        if (dto == null) return ResponseEntity.notFound().build();

        dto.add(linkTo(methodOn(ReporteController.class).buscarPorId(id)).withSelfRel());
        dto.add(linkTo(methodOn(ReporteController.class).listarTodos()).withRel("todos-los-reportes"));

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crear(@Valid @RequestBody ReporteRequestDTO dto) {
        log.info("[ReporteController] POST /api/v1/reportes");
        ReporteResponseDTO creado = reporteService.guardar(dto);

        creado.add(linkTo(methodOn(ReporteController.class).buscarPorId(creado.getReporteId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping("/generar-consolidado")
    public ResponseEntity<ReporteResponseDTO> generarConsolidado() {
        log.info("[ReporteController] POST /api/v1/reportes/generar-consolidado");
        ReporteResponseDTO consolidado = reporteService.generarReporteConsolidado();

        consolidado.add(linkTo(methodOn(ReporteController.class).buscarPorId(consolidado.getReporteId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(consolidado);
    }

    // (Tus métodos PUT y DELETE quedan iguales, solo agregando HATEOAS al PUT si retorna DTO)
    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ReporteRequestDTO dto) {
        ReporteResponseDTO actualizado = reporteService.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        actualizado.add(linkTo(methodOn(ReporteController.class).buscarPorId(actualizado.getReporteId())).withSelfRel());
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!reporteService.eliminar(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}