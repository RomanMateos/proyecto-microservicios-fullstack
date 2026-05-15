package com.fullstackev2.reportes.controller;

import com.fullstackev2.reportes.dto.ReporteDTO;
import com.fullstackev2.reportes.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService service;

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> listar() {
        return ResponseEntity.ok(service.obtenerHistorial());
    }

    @PostMapping("/generar-ventas")
    public ResponseEntity<ReporteDTO> generar() {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.generarReporteVentas());
    }
}