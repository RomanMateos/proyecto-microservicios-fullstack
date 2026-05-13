package com.fullstackev2.proveedores.controller;

import com.fullstackev2.proveedores.dto.ContratoRequestDTO;
import com.fullstackev2.proveedores.dto.ContratoResponseDTO;
import com.fullstackev2.proveedores.service.ContratoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contratos")
@RequiredArgsConstructor
@Slf4j
public class ContratoController {

    private final ContratoService contratoService;

    @GetMapping
    public ResponseEntity<List<ContratoResponseDTO>> listarTodos() {
        log.info("[ContratoController] GET /api/v1/contratos");
        return ResponseEntity.ok(contratoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoResponseDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[ContratoController] GET /api/v1/contratos/{}", id);
        ContratoResponseDTO dto = contratoService.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ContratoResponseDTO> crear(@Valid @RequestBody ContratoRequestDTO dto) {
        log.info("[ContratoController] POST /api/v1/contratos");
        return ResponseEntity.status(HttpStatus.CREATED).body(contratoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ContratoRequestDTO dto) {
        log.info("[ContratoController] PUT /api/v1/contratos/{}", id);
        ContratoResponseDTO actualizado = contratoService.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[ContratoController] DELETE /api/v1/contratos/{}", id);
        if (!contratoService.eliminar(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}