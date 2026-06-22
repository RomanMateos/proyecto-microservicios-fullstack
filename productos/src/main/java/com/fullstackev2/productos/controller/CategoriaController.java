package com.fullstackev2.productos.controller;

import com.fullstackev2.productos.dto.CategoriaDTO;
import com.fullstackev2.productos.service.CategoriaService;
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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("api/v1")
@Tag(name = "Categorias", description = "Operaciones CRUD de categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    @Operation(
            summary = "Listar categorias",
            description = "Obtiene todas las categorias registradas en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Categorias obtenidas correctamente")
    @ApiResponse(responseCode = "404", description = "Categorias no encontradas")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        log.info("[Categoria Controller] Iniciando buscar categorias");
        List<CategoriaDTO> categoria = categoriaService.obtenerCategorias();
        return ResponseEntity.ok().body(categoria);
    }

    @GetMapping("/categorias/{id}")
    @Operation(
            summary = "Buscar categoria por ID",
            description = "Obtiene una categoria por su identificador unico"
    )
    @ApiResponse(responseCode = "200", description = "Categoria encontrada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[Categoria Controller] Iniciando buscar por id");
        Optional<CategoriaDTO> categoria = categoriaService.buscarPorId(id);
        return categoria
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/categorias")
    @Operation(
            summary = "Guardar categoria",
            description = "Registra una nueva categoria en la base de datos"
    )
    @ApiResponse(responseCode = "201", description = "Categoria creada correctamente")
    @ApiResponse(responseCode = "400", description = "Uno o mas datos ingresados son invalidos")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<CategoriaDTO> guardar(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        log.info("[Categoria Controller] Iniciando guardar o categoria");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoriaService.guardar(categoriaDTO));
    }

    @PutMapping("/categorias/{id}")
    @Operation(
            summary = "Actualizar categoria",
            description = "Actualiza los datos de una categoria registrada previamente"
    )
    @ApiResponse(responseCode = "200", description = "Categoria actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    @ApiResponse(responseCode = "400", description = "Uno o mas datos ingresados son invalidos")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<CategoriaDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaDTO categoriaDTO
    ) {
        log.info("[Categoria Controller] Iniciando actualizar o categoria");
        return categoriaService.actualizarPorId(id, categoriaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/categorias/{id}")
    @Operation(
            summary = "Eliminar categoria",
            description = "Elimina una categoria registrada previamente mediante su identificador unico"
    )
    @ApiResponse(responseCode = "204", description = "Categoria eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Categoria Controller] Iniciando eliminar o categoria");
        boolean eliminado = categoriaService.eliminarPorId(id);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categorias/{id}/hateoas")
    @Operation(
            summary = "Buscar categoria por ID con HATEOAS",
            description = "Obtiene una categoria especifica y agrega enlaces relacionados mediante HATEOAS"
    )
    @ApiResponse(responseCode = "200", description = "Categoria encontrada con enlaces HATEOAS")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<EntityModel<CategoriaDTO>> buscarPorIdHateoas(@PathVariable Integer id) {
        log.info("[Categoria Controller] Iniciando buscarPorIdHateoas");

        Optional<CategoriaDTO> categoria = categoriaService.buscarPorId(id);

        if (categoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<CategoriaDTO> recurso = EntityModel.of(categoria.get());

        recurso.add(linkTo(methodOn(CategoriaController.class).buscarPorId(id)).withSelfRel());
        recurso.add(linkTo(methodOn(CategoriaController.class).listarCategorias()).withRel("categorias"));
        recurso.add(linkTo(methodOn(CategoriaController.class).eliminar(id)).withRel("eliminar"));

        return ResponseEntity.ok(recurso);
    }

    @GetMapping("/categorias/hateoas")
    @Operation(
            summary = "Listar categorias con HATEOAS",
            description = "Obtiene todas las categorias registradas y agrega enlaces HATEOAS a cada recurso"
    )
    @ApiResponse(responseCode = "200", description = "Categorias obtenidas con enlaces HATEOAS")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<CollectionModel<EntityModel<CategoriaDTO>>> listarCategoriasHateoas() {
        log.info("[Categoria Controller] Iniciando listarCategoriasHateoas");

        List<EntityModel<CategoriaDTO>> categorias = categoriaService.obtenerCategorias()
                .stream()
                .map(categoria -> {
                    EntityModel<CategoriaDTO> recurso = EntityModel.of(categoria);

                    recurso.add(linkTo(methodOn(CategoriaController.class)
                            .buscarPorId(categoria.getId())).withSelfRel());

                    recurso.add(linkTo(methodOn(CategoriaController.class)
                            .buscarPorIdHateoas(categoria.getId())).withRel("hateoas"));

                    recurso.add(linkTo(methodOn(CategoriaController.class)
                            .eliminar(categoria.getId())).withRel("eliminar"));

                    return recurso;
                })
                .toList();

        CollectionModel<EntityModel<CategoriaDTO>> coleccion = CollectionModel.of(categorias);

        coleccion.add(linkTo(methodOn(CategoriaController.class).listarCategorias()).withRel("categorias"));
        coleccion.add(linkTo(methodOn(CategoriaController.class).listarCategoriasHateoas()).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }
}