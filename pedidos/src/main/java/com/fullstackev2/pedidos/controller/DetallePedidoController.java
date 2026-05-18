package com.fullstackev2.pedidos.controller;

import com.fullstackev2.pedidos.dto.DetallePedidoDTO;
import com.fullstackev2.pedidos.dto.DetallePedidoRequestDTO;
import com.fullstackev2.pedidos.service.DetallePedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> listarTodos() {
        return ResponseEntity.ok(detallePedidoService.listarDetallePedido());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> obtenerPorId(@PathVariable Integer id) {
        Optional<DetallePedidoDTO> detalle = detallePedidoService.buscarPorId(id);
        return detalle
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetallePedidoDTO> crear(
            @Valid @RequestBody DetallePedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(detallePedidoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DetallePedidoRequestDTO dto) {
        return ResponseEntity.ok(detallePedidoService.actualizarPorId(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        detallePedidoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}