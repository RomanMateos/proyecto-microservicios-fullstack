package com.fullstackev2.sucursales.controller;

import com.fullstackev2.sucursales.dto.RegionRequestDTO;
import com.fullstackev2.sucursales.dto.RegionResponseDTO;
import com.fullstackev2.sucursales.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/regiones")
@RequiredArgsConstructor
@Slf4j
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionResponseDTO>> listarTodos() {
        log.info("[RegionController] GET /api/v1/regiones");
        return ResponseEntity.ok(regionService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionResponseDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[RegionController] GET /api/v1/regiones/{}", id);
        RegionResponseDTO dto = regionService.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<RegionResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        log.info("[RegionController] GET /api/v1/regiones/buscar?nombre={}", nombre);
        return ResponseEntity.ok(regionService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<RegionResponseDTO> crear(@Valid @RequestBody RegionRequestDTO dto) {
        log.info("[RegionController] POST /api/v1/regiones");
        return ResponseEntity.status(HttpStatus.CREATED).body(regionService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody RegionRequestDTO dto) {
        log.info("[RegionController] PUT /api/v1/regiones/{}", id);
        RegionResponseDTO actualizado = regionService.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[RegionController] DELETE /api/v1/regiones/{}", id);
        if (!regionService.eliminar(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}