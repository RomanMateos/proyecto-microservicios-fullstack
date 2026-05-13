package com.fullstackev2.ms_pedidos.controller;

import com.fullstackev2.ms_pedidos.dto.DetallePedidoDTO;
import com.fullstackev2.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.fullstackev2.ms_pedidos.service.DetallePedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> getAll() {
        return ResponseEntity.ok(detallePedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(detallePedidoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DetallePedidoDTO> create(
            @Valid @RequestBody DetallePedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(detallePedidoService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> update(@PathVariable Integer id,
                                                   @Valid @RequestBody DetallePedidoRequestDTO dto) {
        return ResponseEntity.ok(detallePedidoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        detallePedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}