package com.fullstackev2.envios.controller;

import com.fullstackev2.envios.dto.SeguimientoDTO;
import com.fullstackev2.envios.model.Seguimiento;
import com.fullstackev2.envios.repository.SeguimientoRepository;
import com.fullstackev2.envios.service.SeguimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class SeguimientoController {
    @Autowired
    private SeguimientoService seguimientoService;
    @GetMapping("/seguimientos")
    public ResponseEntity<List<SeguimientoDTO>> seguimientos(){
        List<SeguimientoDTO> seg = seguimientoService.listarSeguimientos();
        return ResponseEntity.ok().body(seg);
    }
    @GetMapping("/seguimientos/{id}")
    public ResponseEntity<SeguimientoDTO> buscarPorId(@PathVariable Integer id){
        Optional<SeguimientoDTO> seguimiento = seguimientoService.buscarPorId(id);
        return seguimiento
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/seguimientos")
    public ResponseEntity<SeguimientoDTO> guardar(@Valid @RequestBody SeguimientoDTO seguimientoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(seguimientoService.guardar(seguimientoDTO));
    }
    @PutMapping("/seguimientos/{id}")
    public ResponseEntity<SeguimientoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody SeguimientoDTO dto){
        return seguimientoService.actualizarPorId(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/seguimientos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        boolean eliminado = seguimientoService.eliminarPorId(id);
        if(eliminado){return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
    }

}
