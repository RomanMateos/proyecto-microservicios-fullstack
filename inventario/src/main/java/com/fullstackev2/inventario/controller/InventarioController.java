package com.fullstackev2.inventario.controller;

import com.fullstackev2.inventario.dto.InventarioDTO;
import com.fullstackev2.inventario.service.InventarioService;
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
@RequestMapping("api/v1")
@Tag(name = "ms-inventario", description = "Operaciones CRUD, documentación OpenAPI y enlaces hipermedia de Inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/inventario")
    @Operation(summary = "Listar todo el inventario", description = "Obtiene los registros de inventario con enlaces hipermedia HATEOAS.")
    @ApiResponse(responseCode = "200", description = "Inventario obtenido correctamente")
    public ResponseEntity<CollectionModel<EntityModel<InventarioDTO>>> listarInventario() {
        log.info("[Inventario Controller] listarInventario con HATEOAS");
        List<InventarioDTO> inventario = inventarioService.listarInventario();

        List<EntityModel<InventarioDTO>> recursos = inventario.stream()
                .map(item -> EntityModel.of(item,
                        linkTo(methodOn(InventarioController.class).buscarPorId(item.getId())).withSelfRel()))
                .toList();

        CollectionModel<EntityModel<InventarioDTO>> coleccion = CollectionModel.of(recursos,
                linkTo(methodOn(InventarioController.class).listarInventario()).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }

    @GetMapping("/inventario/{id}")
    @Operation(summary = "Buscar ítem por ID", description = "Obtiene un único registro de inventario con enlaces dinámicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem de inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró el ítem de inventario")
    })
    public ResponseEntity<EntityModel<InventarioDTO>> buscarPorId(@PathVariable Integer id) {
        log.info("[Inventario Controller] buscarPorId");
        return inventarioService.buscarPorId(id)
                .map(item -> ResponseEntity.ok(EntityModel.of(item,
                        linkTo(methodOn(InventarioController.class).buscarPorId(id)).withSelfRel(),
                        linkTo(methodOn(InventarioController.class).listarInventario()).withRel("todos-los-inventarios"))))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/inventario")
    @Operation(summary = "Guardar un nuevo ítem", description = "Registra un producto en el inventario.")
    @ApiResponse(responseCode = "201", description = "Ítem creado con éxito")
    public ResponseEntity<EntityModel<InventarioDTO>> guardar(@Valid @RequestBody InventarioDTO inventarioDTO) {
        log.info("[Inventario Controller] guardar");
        InventarioDTO creado = inventarioService.guardar(inventarioDTO);

        EntityModel<EntityModel<InventarioDTO>> recurso = EntityModel.of(EntityModel.of(creado),
                linkTo(methodOn(InventarioController.class).buscarPorId(creado.getId())).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(creado,
                linkTo(methodOn(InventarioController.class).buscarPorId(creado.getId())).withSelfRel()));
    }

    @PutMapping("/inventario/{id}")
    @Operation(summary = "Actualizar un registro", description = "Modifica un ítem de inventario existente por su ID.")
    @ApiResponse(responseCode = "200", description = "Ítem actualizado con éxito")
    public ResponseEntity<EntityModel<InventarioDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody InventarioDTO inventarioDTO) {
        log.info("[Inventario Controller] actualizar");
        return inventarioService.actualizarPorId(id, inventarioDTO)
                .map(actualizado -> ResponseEntity.ok(EntityModel.of(actualizado,
                        linkTo(methodOn(InventarioController.class).buscarPorId(id)).withSelfRel())))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/inventario/{id}")
    @Operation(summary = "Eliminar un ítem", description = "Remueve permanentemente un registro de inventario.")
    @ApiResponse(responseCode = "204", description = "Registro eliminado con éxito")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Inventario Controller] eliminar");
        boolean eliminado = inventarioService.eliminarPorId(id);
        if (eliminado) { return ResponseEntity.noContent().build(); }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/inventario/activos")
    @Operation(summary = "Listar inventarios activos", description = "Obtiene los ítems activos que superen un mínimo de cantidad.")
    @ApiResponse(responseCode = "200", description = "Filtro de activos completado")
    public ResponseEntity<CollectionModel<EntityModel<InventarioDTO>>> listarActivos(@RequestParam Integer cantidad) {
        log.info("[Inventario Controller] listarActivos");
        List<InventarioDTO> activos = inventarioService.listarInventarioActivos(cantidad);

        List<EntityModel<InventarioDTO>> recursos = activos.stream()
                .map(item -> EntityModel.of(item,
                        linkTo(methodOn(InventarioController.class).buscarPorId(item.getId())).withSelfRel()))
                .toList();

        return ResponseEntity.ok(CollectionModel.of(recursos));
    }
}