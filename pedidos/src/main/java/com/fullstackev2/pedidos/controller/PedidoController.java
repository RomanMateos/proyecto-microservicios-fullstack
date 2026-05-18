package com.fullstackev2.pedidos.controller;

import com.fullstackev2.pedidos.dto.PedidoDTO;
import com.fullstackev2.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        log.info("[Pedido Controller] Iniciando lista de pedidos");
        List<PedidoDTO> pedido = pedidoService.obtenerPedidos();
        return ResponseEntity.ok().body(pedido);
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando obtenerPorId");
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PostMapping("/pedidos")
    public ResponseEntity<PedidoDTO> crear(@Valid @RequestBody PedidoDTO dto) {
        log.info("[Pedido Controller] Iniciando crear");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.guardar(dto));
    }

    @PutMapping("/pedidos/{id}")
    public ResponseEntity<PedidoDTO> actualizar(@PathVariable Integer id,
                                                @Valid @RequestBody PedidoDTO dto) {
        log.info("[Pedido Controller] Iniciando actualizar");
        return pedidoService.actualizarPorId(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando eliminar");
        boolean eliminado = pedidoService.eliminarPorId(id);
        if (eliminado) { return ResponseEntity.noContent().build(); }{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/pedidos/{id}/total")
    public ResponseEntity<Double> obtenerTotalPedido(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando obtenerTotalPedido");
        Double total = pedidoService.obtenerTotalPedido(id);
        return ResponseEntity.ok(total);
    }
}