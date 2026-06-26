package com.fullstackev2.pagos.controller;

import com.fullstackev2.pagos.dto.PagoDTO;
import com.fullstackev2.pagos.service.PagoService;
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
@Tag(name = "ms-pagos", description = "Operaciones CRUD, documentación OpenAPI y enlaces hipermedia de Pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping("/pagos")
    @Operation(summary = "Listar todos los pagos", description = "Obtiene los registros de pagos enriquecidos con enlaces dinámicos HATEOAS.")
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
    @Operation(summary = "Buscar pago por ID", description = "Obtiene los detalles de un pago individual asociado a un hiperenlace.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago localizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ID del pago solicitado no existe")
    })
    public ResponseEntity<EntityModel<PagoDTO>> buscarPorId(@PathVariable Integer id) {
        log.info("[Pago Controller] Iniciando obtencion de pago individual");
        return pagoService.buscarPorId(id)
                .map(pago -> ResponseEntity.ok(EntityModel.of(pago,
                        linkTo(methodOn(PagoController.class).buscarPorId(id)).withSelfRel(),
                        linkTo(methodOn(PagoController.class).listarPagos()).withRel("todos-los-pagos"))))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pagos")
    @Operation(summary = "Registrar un pago", description = "Crea un nuevo comprobante de pago en el sistema validando la estructura del DTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago registrado con éxito"),
            @ApiResponse(responseCode = "400", description = "Cuerpo de la petición inválido o mal estructurado")
    })
    public ResponseEntity<EntityModel<PagoDTO>> guardar(@Valid @RequestBody PagoDTO pagoDTO) {
        log.info("[Pago Controller] Iniciando guardar pago");
        PagoDTO creado = pagoService.guardar(pagoDTO);

        EntityModel<PagoDTO> recurso = EntityModel.of(creado,
                linkTo(methodOn(PagoController.class).buscarPorId(creado.getId())).withSelfRel());

        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @PutMapping("/pagos/{id}")
    @Operation(summary = "Actualizar pago por ID", description = "Modifica los valores de un pago existente referenciado por su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago modificado de forma exitosa"),
            @ApiResponse(responseCode = "404", description = "No se pudo actualizar porque el ID de pago no existe")
    })
    public ResponseEntity<EntityModel<PagoDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody PagoDTO pago) {
        log.info("[Pago Controller] Iniciando actualizar");
        return pagoService.actualizarPorId(id, pago)
                .map(actualizado -> ResponseEntity.ok(EntityModel.of(actualizado,
                        linkTo(methodOn(PagoController.class).buscarPorId(id)).withSelfRel())))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/pagos/{id}")
    @Operation(summary = "Eliminar un registro de pago", description = "Remueve físicamente el registro del pago correspondiente al ID proveído.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro de pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el registro para eliminar")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Pago Controller] Iniciando eliminar");
        boolean eliminado = pagoService.eliminarPorId(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/pagos/totales")
    @Operation(summary = "Filtrar pagos por condiciones", description = "Retorna una lista enriquecida con enlaces hipermedia filtrando por monto mínimo y estado de aceptación.")
    @ApiResponse(responseCode = "200", description = "Filtro de pagos completado con éxito")
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> obtenerTotales(@RequestParam Double monto, @RequestParam Boolean aceptado) {
        log.info("[Pago Controller] Iniciando obtencion de pagos filtrados con HATEOAS");
        List<PagoDTO> filtrados = pagoService.buscarPagos(monto, aceptado);

        List<EntityModel<PagoDTO>> recursos = filtrados.stream()
                .map(pago -> EntityModel.of(pago,
                        linkTo(methodOn(PagoController.class).buscarPorId(pago.getId())).withSelfRel()))
                .toList();

        CollectionModel<EntityModel<PagoDTO>> coleccion = CollectionModel.of(recursos,
                linkTo(methodOn(PagoController.class).obtenerTotales(monto, aceptado)).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }
}