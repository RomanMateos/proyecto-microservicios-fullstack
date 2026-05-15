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

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
@Slf4j
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> listarTodos() {
        log.info("[ReporteController] GET /api/v1/reportes");
        return ResponseEntity.ok(reporteService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ReporteResponseDTO>> listarActivos() {
        log.info("[ReporteController] GET /api/v1/reportes/activos");
        return ResponseEntity.ok(reporteService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[ReporteController] GET /api/v1/reportes/{}", id);
        ReporteResponseDTO dto = reporteService.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crear(@Valid @RequestBody ReporteRequestDTO dto) {
        log.info("[ReporteController] POST /api/v1/reportes");
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.guardar(dto));
    }

    @PostMapping("/generar-consolidado")
    public ResponseEntity<ReporteResponseDTO> generarConsolidado() {
        log.info("[ReporteController] POST /api/v1/reportes/generar-consolidado");
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.generarReporteConsolidado());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ReporteRequestDTO dto) {
        log.info("[ReporteController] PUT /api/v1/reportes/{}", id);
        ReporteResponseDTO actualizado = reporteService.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[ReporteController] DELETE /api/v1/reportes/{}", id);
        if (!reporteService.eliminar(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}