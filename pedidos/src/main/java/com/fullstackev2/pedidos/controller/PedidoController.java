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
@Tag(name = "ms-pedidos", description = "Operaciones CRUD, documentación OpenAPI y enlaces hipermedia de Pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    @Operation(summary = "Listar todos los pedidos", description = "Obtiene una lista de todos los pedidos registrados junto con sus enlaces HATEOAS correspondientes.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente")
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
    @Operation(summary = "Obtener pedido por ID", description = "Busca un único pedido según su identificador único y genera enlaces dinámicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado y procesado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID del pedido solicitado no existe en la base de datos")
    })
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
    @Operation(summary = "Crear nuevo pedido", description = "Registra un nuevo pedido en el sistema, validando los datos obligatorios entrantes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado de manera exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o mal estructurados")
    })
    public ResponseEntity<EntityModel<PedidoDTO>> crear(@Valid @RequestBody PedidoDTO dto) {
        log.info("[Pedido Controller] Iniciando crear con HATEOAS");
        PedidoDTO creado = pedidoService.guardar(dto);

        EntityModel<PedidoDTO> recurso = EntityModel.of(creado,
                linkTo(methodOn(PedidoController.class).obtenerPorId(creado.getId())).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @PutMapping("/pedidos/{id}")
    @Operation(summary = "Actualizar un pedido", description = "Modifica todos los atributos editables de un pedido existente identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado de forma exitosa"),
            @ApiResponse(responseCode = "404", description = "No se pudo actualizar debido a que el ID del pedido no existe")
    })
    public ResponseEntity<EntityModel<PedidoDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody PedidoDTO dto) {
        log.info("[Pedido Controller] Iniciando actualizar");
        return pedidoService.actualizarPorId(id, dto)
                .map(actualizado -> ResponseEntity.ok(EntityModel.of(actualizado,
                        linkTo(methodOn(PedidoController.class).obtenerPorId(id)).withSelfRel())))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/pedidos/{id}")
    @Operation(summary = "Eliminar un pedido", description = "Remueve físicamente el registro de un pedido del sistema mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido removido correctamente del sistema"),
            @ApiResponse(responseCode = "404", description = "No se encontró el pedido correspondiente al ID enviado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando eliminar");
        boolean eliminado = pedidoService.eliminarPorId(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/pedidos/{id}/total")
    @Operation(summary = "Obtener total monetario del pedido", description = "Calcula el costo total consultando síncronamente el precio real de cada ítem al microservicio de productos vía Feign.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monto monetario total calculado y devuelto con éxito"),
            @ApiResponse(responseCode = "404", description = "No se encontró el pedido o algún producto relacionado")
    })
    public ResponseEntity<Double> obtenerTotalPedido(@PathVariable Integer id) {
        log.info("[Pedido Controller] Iniciando obtenerTotalPedido");
        return ResponseEntity.ok(pedidoService.obtenerTotalPedido(id));
    }
}