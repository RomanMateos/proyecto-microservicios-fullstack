package com.fullstackev2.pagos.controller;

import com.fullstackev2.pagos.dto.PagoDTO;
import com.fullstackev2.pagos.service.PagoService;
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
@RequestMapping("api/v1")
@Tag(name = "Pagos", description = "Operaciones CRUD y enlaces hipermedia de Pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/pagos")
    @Operation(summary = "Listar todos los pagos", description = "Obtiene los registros de pagos enriquecidos con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Pagos listados correctamente")
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> listarPagos() {
        log.info("[Pago Controller] Iniciando obtencion de pagos con HATEOAS");

        List<EntityModel<PagoDTO>> pagos = pagoService.obtenerTotalPedidos().stream()
                .map(pago -> EntityModel.of(pago,
                        linkTo(methodOn(PagoController.class).buscarPorId(pago.getId())).withSelfRel()))
                .toList();

        CollectionModel<EntityModel<PagoDTO>> coleccion = CollectionModel.of(pagos,
                linkTo(methodOn(PagoController.class).listarPagos()).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }

    @GetMapping("/pagos/{id}")
    @Operation(summary = "Buscar pago por ID", description = "Obtiene los detalles de un pago individual con hiperenlaces")
    @ApiResponse(responseCode = "200", description = "Pago localizado")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    public ResponseEntity<EntityModel<PagoDTO>> buscarPorId(@PathVariable Integer id) {
        log.info("[Pago Controller] Iniciando obtencion de pago individual");
        return pagoService.buscarPorId(id)
                .map(pago -> ResponseEntity.ok(EntityModel.of(pago,
                        linkTo(methodOn(PagoController.class).buscarPorId(id)).withSelfRel(),
                        linkTo(methodOn(PagoController.class).listarPagos()).withRel("todos-los-pagos"))))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pagos")
    @Operation(summary = "Registrar un pago", description = "Crea un nuevo comprobante de pago")
    public ResponseEntity<EntityModel<PagoDTO>> guardar(@Valid @RequestBody PagoDTO pagoDTO) {
        log.info("[Pago Controller] Iniciando guardar pago");
        PagoDTO creado = pagoService.guardar(pagoDTO);

        EntityModel<PagoDTO> recurso = EntityModel.of(creado,
                linkTo(methodOn(PagoController.class).buscarPorId(creado.getId())).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @PutMapping("/pagos/{id}")
    @Operation(summary = "Actualizar pago por ID", description = "Modifica los valores registrados de un pago")
    public ResponseEntity<EntityModel<PagoDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody PagoDTO pago) {
        log.info("[Pago Controller] Iniciando actualizar");
        return pagoService.actualizarPorId(id, pago)
                .map(actualizado -> ResponseEntity.ok(EntityModel.of(actualizado,
                        linkTo(methodOn(PagoController.class).buscarPorId(id)).withSelfRel())))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/pagos/{id}")
    @Operation(summary = "Eliminar un registro de pago")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Pago Controller] Iniciando eliminar");
        boolean eliminado = pagoService.eliminarPorId(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/pagos/totales")
    @Operation(summary = "Filtrar pagos por condiciones", description = "Retorna una lista filtrada por monto y estado de aceptación")
    public ResponseEntity<List<PagoDTO>> obtenerTotales(@RequestParam Double monto, @RequestParam Boolean aceptado) {
        log.info("[Pago Controller] Iniciando obtencion de pagos filtrados");
        return ResponseEntity.ok(pagoService.buscarPagos(monto, aceptado));
    }
}