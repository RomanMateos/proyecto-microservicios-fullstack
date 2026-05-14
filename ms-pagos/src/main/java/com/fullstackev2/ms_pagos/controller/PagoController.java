package com.fullstackev2.ms_pagos.controller;

import com.fullstackev2.ms_pagos.dto.PagoDTO;
import com.fullstackev2.ms_pagos.dto.PagoRequestDTO;
import com.fullstackev2.ms_pagos.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PagoDTO> crear(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> actualizar(@PathVariable Integer id,
                                              @Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}