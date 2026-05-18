package com.fullstackev2.inventario.controller;

import com.fullstackev2.inventario.dto.InventarioDTO;
import com.fullstackev2.inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;
    @GetMapping("/inventario")
    public ResponseEntity<List<InventarioDTO>> listarInventario() {
        log.info("[Inventario Controller] listarInventario");
        List<InventarioDTO> inventario = inventarioService.listarInventario();
        return ResponseEntity.ok().body(inventario);
    }
    @GetMapping("/inventario/{id}")
    public ResponseEntity<InventarioDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[Inventario Controller] buscarPorId");
        Optional<InventarioDTO> inventario = inventarioService.buscarPorId(id);
        return inventario
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/inventario")
    public ResponseEntity<InventarioDTO> guardar(@Valid @RequestBody InventarioDTO inventarioDTO) {
        log.info("[Inventario Controller] guardar");
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.guardar(inventarioDTO));
    }
    @PutMapping("/inventario/{id}")
    public ResponseEntity<InventarioDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody InventarioDTO inventarioDTO) {
        log.info("[Inventario Controller] actualizar");
        return inventarioService.actualizarPorId(id,inventarioDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/inventario/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Inventario Controller] eliminar");
        boolean eliminado = inventarioService.eliminarPorId(id);
        if (eliminado) { return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/inventario/activos")
    public ResponseEntity<List<InventarioDTO>> listarActivos(@RequestParam Integer cantidad) {
        log.info("[Inventario Controller] listarActivos");
        return ResponseEntity.ok(inventarioService.listarInventarioActivos(cantidad));
    }
}
