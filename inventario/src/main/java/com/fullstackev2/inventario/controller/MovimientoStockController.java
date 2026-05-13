package com.fullstackev2.inventario.controller;

import com.fullstackev2.inventario.dto.MovimientoStockDTO;
import com.fullstackev2.inventario.service.MovimientoStockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class MovimientoStockController {
    @Autowired
    private MovimientoStockService movimientoStockService;
    @GetMapping("/movimientos")
    public ResponseEntity<List<MovimientoStockDTO>> obtenerMovimientos(){
        List<MovimientoStockDTO> mov =  movimientoStockService.obtenerMovimientos();
        return ResponseEntity.ok(mov);
    }
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<MovimientoStockDTO> buscarPorId(@PathVariable Integer id){
        Optional<MovimientoStockDTO> mov =  movimientoStockService.buscarPorId(id);
        return mov
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/movimientos")
    public ResponseEntity<MovimientoStockDTO> guardar(@Valid @RequestBody MovimientoStockDTO mov){
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoStockService.guardar(mov));
    }
    @PutMapping("/movimientos/{id}")
    public ResponseEntity<MovimientoStockDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody MovimientoStockDTO mov){
        return movimientoStockService.actualizarPorId(id,mov)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/movimientos/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id){
        boolean eliminado =  movimientoStockService.eliminarPorId(id);
        if (eliminado) { return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
    }

}
