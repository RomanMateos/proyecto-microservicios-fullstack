package com.fullstackev2.pedidos.controller;

import com.fullstackev2.pedidos.dto.PedidoDTO;
import com.fullstackev2.pedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "API para gestión de pedidos del e-commerce")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Listar todos los pedidos")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        log.info("[Pedido Controller] Iniciando listarTodos");
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando obtenerPorId: {}", id);
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @Operation(summary = "Crear nuevo pedido", description = "Valida usuario via FeignClient antes de crear")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@Valid @RequestBody PedidoDTO dto) {
        log.info("[Pedido Controller] Iniciando crear pedido");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.guardar(dto));
    }

    @Operation(summary = "Actualizar pedido existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> actualizar(@PathVariable Integer id,
                                                @Valid @RequestBody PedidoDTO dto) {
        log.info("[Pedido Controller] Iniciando actualizar: {}", id);
        return ResponseEntity.ok(pedidoService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido eliminado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando eliminar: {}", id);
        boolean eliminado = pedidoService.eliminarPorId(id);
        if (eliminado) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}