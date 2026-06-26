package com.fullstackev2.pedidos.controller;

import com.fullstackev2.pedidos.dto.PedidoDTO;
import com.fullstackev2.pedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Pedidos", description = "Operaciones CRUD y enlaces hipermedia de Pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    @Operation(summary = "Listar todos los pedidos", description = "Obtiene los pedidos con enlaces hipermedia")
    @ApiResponse(responseCode = "200", description = "Pedidos obtenidos correctamente")
    public ResponseEntity<CollectionModel<EntityModel<PedidoDTO>>> listarTodos() {
        log.info("[Pedido Controller] Iniciando lista de pedidos con HATEOAS");

        List<PedidoDTO> listaDtos = pedidoService.obtenerPedidos();

        List<EntityModel<PedidoDTO>> pedidos = listaDtos.stream()
                .map(pedido -> EntityModel.of(pedido,
                        linkTo(methodOn(PedidoController.class).obtenerPorId(pedido.getId())).withSelfRel(),
                        linkTo(methodOn(PedidoController.class).obtenerTotalPedido(pedido.getId())).withRel("total-pedido")))
                .toList();

        CollectionModel<EntityModel<PedidoDTO>> coleccion = CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }

    @GetMapping("/pedidos/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Obtiene un único pedido agregando enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    public ResponseEntity<EntityModel<PedidoDTO>> obtenerPorId(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando obtenerPorId con HATEOAS");
        PedidoDTO dto = pedidoService.buscarPorId(id);

        EntityModel<PedidoDTO> recurso = EntityModel.of(dto,
                linkTo(methodOn(PedidoController.class).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarTodos()).withRel("todos-los-pedidos"),
                linkTo(methodOn(PedidoController.class).obtenerTotalPedido(id)).withRel("total-pedido"));

        return ResponseEntity.ok(recurso);
    }

    @PostMapping("/pedidos")
    @Operation(summary = "Crear nuevo pedido", description = "Registra un pedido y retorna su enlace de consulta")
    @ApiResponse(responseCode = "201", description = "Pedido creado con éxito")
    public ResponseEntity<EntityModel<PedidoDTO>> crear(@Valid @RequestBody PedidoDTO dto) {
        log.info("[Pedido Controller] Iniciando crear con HATEOAS");
        PedidoDTO creado = pedidoService.guardar(dto);

        EntityModel<PedidoDTO> recurso = EntityModel.of(creado,
                linkTo(methodOn(PedidoController.class).obtenerPorId(creado.getId())).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @PutMapping("/pedidos/{id}")
    @Operation(summary = "Actualizar un pedido", description = "Modifica los datos de un pedido existente")
    @ApiResponse(responseCode = "200", description = "Pedido actualizado con éxito")
    public ResponseEntity<EntityModel<PedidoDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody PedidoDTO dto) {
        log.info("[Pedido Controller] Iniciando actualizar");
        return pedidoService.actualizarPorId(id, dto)
                .map(actualizado -> ResponseEntity.ok(EntityModel.of(actualizado,
                        linkTo(methodOn(PedidoController.class).obtenerPorId(id)).withSelfRel())))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/pedidos/{id}")
    @Operation(summary = "Eliminar un pedido", description = "Remueve un pedido permanentemente")
    @ApiResponse(responseCode = "204", description = "Pedido eliminado con éxito")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando eliminar");
        boolean eliminado = pedidoService.eliminarPorId(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/pedidos/{id}/total")
    @Operation(summary = "Obtener total monetario del pedido", description = "Calcula el coste total de los elementos del pedido")
    @ApiResponse(responseCode = "200", description = "Total calculado con éxito")
    public ResponseEntity<Double> obtenerTotalPedido(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando obtenerTotalPedido");
        return ResponseEntity.ok(pedidoService.obtenerTotalPedido(id));
    }
}