package com.fullstackev2.empleados.controller;

import com.fullstackev2.empleados.dto.EmpleadoRequestDTO;
import com.fullstackev2.empleados.dto.EmpleadoResponseDTO;
import com.fullstackev2.empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
@Slf4j
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listarTodos() {
        log.info("[EmpleadoController] GET /api/v1/empleados");
        return ResponseEntity.ok(empleadoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[EmpleadoController] GET /api/v1/empleados/{}", id);
        EmpleadoResponseDTO dto = empleadoService.buscarPorId(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EmpleadoResponseDTO>> buscarPorSucursalYAnio(
            @RequestParam Integer sucursalId,
            @RequestParam Integer anio) {
        log.info("[EmpleadoController] GET /buscar?sucursalId={}&anio={}", sucursalId, anio);
        return ResponseEntity.ok(empleadoService.buscarPorSucursalYAnio(sucursalId, anio));
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(@Valid @RequestBody EmpleadoRequestDTO dto) {
        log.info("[EmpleadoController] POST /api/v1/empleados");
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        log.info("[EmpleadoController] PUT /api/v1/empleados/{}", id);
        EmpleadoResponseDTO actualizado = empleadoService.actualizar(id, dto);
        if (actualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[EmpleadoController] DELETE /api/v1/empleados/{}", id);
        if (!empleadoService.eliminar(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}