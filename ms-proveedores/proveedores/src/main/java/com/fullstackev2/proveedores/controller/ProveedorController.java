package com.fullstackev2.proveedores.controller;

import com.fullstackev2.proveedores.dto.ProveedorRequestDTO;
import com.fullstackev2.proveedores.dto.ProveedorResponseDTO;
import com.fullstackev2.proveedores.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedores")
@RequiredArgsConstructor
@Slf4j
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> listarTodos() {
        log.info("[ProveedorController] GET /api/v1/proveedores");
        return ResponseEntity.ok(proveedorService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<ProveedorResponseDTO>> listarActivos() {
        log.info("[ProveedorController] GET /api/v1/proveedores/activos");
        return ResponseEntity.ok(proveedorService.listarActivosOrdenados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[ProveedorController] GET /api/v1/proveedores/{}", id);
        ProveedorResponseDTO dto = proveedorService.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(@Valid @RequestBody ProveedorRequestDTO dto) {
        log.info("[ProveedorController] POST /api/v1/proveedores");
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProveedorRequestDTO dto) {
        log.info("[ProveedorController] PUT /api/v1/proveedores/{}", id);
        ProveedorResponseDTO actualizado = proveedorService.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[ProveedorController] DELETE /api/v1/proveedores/{}", id);
        if (!proveedorService.eliminar(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}