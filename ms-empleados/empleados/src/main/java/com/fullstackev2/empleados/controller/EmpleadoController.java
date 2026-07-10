package com.fullstackev2.empleados.controller;

import com.fullstackev2.empleados.dto.EmpleadoRequestDTO;
import com.fullstackev2.empleados.dto.EmpleadoResponseDTO;
import com.fullstackev2.empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// IMPORTS DE HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
@Slf4j
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listarTodos() {
        log.info("[EmpleadoController] GET /api/v1/empleados");
        List<EmpleadoResponseDTO> lista = empleadoService.listarTodos();

        lista.forEach(dto ->
                dto.add(linkTo(methodOn(EmpleadoController.class).buscarPorId(dto.getEmpleadoId())).withSelfRel())
        );

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[EmpleadoController] GET /api/v1/empleados/{}", id);
        EmpleadoResponseDTO dto = empleadoService.buscarPorId(id);

        if (dto == null) return ResponseEntity.notFound().build();

        dto.add(linkTo(methodOn(EmpleadoController.class).buscarPorId(id)).withSelfRel());
        dto.add(linkTo(methodOn(EmpleadoController.class).listarTodos()).withRel("todos-los-empleados"));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EmpleadoResponseDTO>> buscarPorSucursalYAnio(
            @RequestParam Integer sucursalId,
            @RequestParam Integer anio) {
        log.info("[EmpleadoController] GET /buscar?sucursalId={}&anio={}", sucursalId, anio);
        List<EmpleadoResponseDTO> lista = empleadoService.buscarPorSucursalYAnio(sucursalId, anio);

        lista.forEach(dto ->
                dto.add(linkTo(methodOn(EmpleadoController.class).buscarPorId(dto.getEmpleadoId())).withSelfRel())
        );

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crear(@Valid @RequestBody EmpleadoRequestDTO dto) {
        log.info("[EmpleadoController] POST /api/v1/empleados");
        EmpleadoResponseDTO creado = empleadoService.guardar(dto);

        creado.add(linkTo(methodOn(EmpleadoController.class).buscarPorId(creado.getEmpleadoId())).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EmpleadoRequestDTO dto) {
        log.info("[EmpleadoController] PUT /api/v1/empleados/{}", id);
        EmpleadoResponseDTO actualizado = empleadoService.actualizar(id, dto);

        if (actualizado == null) return ResponseEntity.notFound().build();

        actualizado.add(linkTo(methodOn(EmpleadoController.class).buscarPorId(actualizado.getEmpleadoId())).withSelfRel());

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[EmpleadoController] DELETE /api/v1/empleados/{}", id);
        if (!empleadoService.eliminar(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}