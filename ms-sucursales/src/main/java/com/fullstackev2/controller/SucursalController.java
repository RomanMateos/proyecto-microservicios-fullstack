package com.fullstackev2.controller;

import com.fullstackev2.sucursales.dto.SucursalRequestDTO;
import com.fullstackev2.sucursales.dto.SucursalResponseDTO;
import com.fullstackev2.sucursales.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// IMPORTS DE HATEOAS (MAGIA PURA)
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/sucursales")
@RequiredArgsConstructor
@Slf4j
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalResponseDTO>> listarTodos() {
        log.info("[SucursalController] GET /api/v1/sucursales");
        List<SucursalResponseDTO> lista = sucursalService.listarTodos();

        // HATEOAS: Agrega el link "self" a cada sucursal de la lista
        lista.forEach(dto ->
                dto.add(linkTo(methodOn(SucursalController.class).buscarPorId(dto.getSucursalId())).withSelfRel())
        );

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[SucursalController] GET /api/v1/sucursales/{}", id);
        SucursalResponseDTO dto = sucursalService.buscarPorId(id);

        if (dto == null) return ResponseEntity.notFound().build();

        // HATEOAS: Link a sí mismo y a la colección completa
        dto.add(linkTo(methodOn(SucursalController.class).buscarPorId(id)).withSelfRel());
        dto.add(linkTo(methodOn(SucursalController.class).listarTodos()).withRel("todas-las-sucursales"));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<SucursalResponseDTO>> buscarPorRegion(@RequestParam String nombreRegion) {
        log.info("[SucursalController] GET /api/v1/sucursales/buscar?nombreRegion={}", nombreRegion);
        List<SucursalResponseDTO> lista = sucursalService.buscarPorNombreRegion(nombreRegion);

        // HATEOAS
        lista.forEach(dto ->
                dto.add(linkTo(methodOn(SucursalController.class).buscarPorId(dto.getSucursalId())).withSelfRel())
        );

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<SucursalResponseDTO> crear(@Valid @RequestBody SucursalRequestDTO dto) {
        log.info("[SucursalController] POST /api/v1/sucursales");
        SucursalResponseDTO creado = sucursalService.guardar(dto);

        // HATEOAS
        creado.add(linkTo(methodOn(SucursalController.class).buscarPorId(creado.getSucursalId())).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody SucursalRequestDTO dto) {
        log.info("[SucursalController] PUT /api/v1/sucursales/{}", id);
        SucursalResponseDTO actualizado = sucursalService.actualizar(id, dto);

        if (actualizado == null) return ResponseEntity.notFound().build();

        // HATEOAS
        actualizado.add(linkTo(methodOn(SucursalController.class).buscarPorId(actualizado.getSucursalId())).withSelfRel());

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[SucursalController] DELETE /api/v1/sucursales/{}", id);
        if (!sucursalService.eliminar(id)) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }
}