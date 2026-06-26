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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1")
@Tag(name = "Pagos", description = "API para gestión de pagos del e-commerce")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Listar todos los pagos")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping("/pagos")
    public ResponseEntity<List<PagoDTO>> listarPagos() {
        log.info("[Pago Controller] Iniciando obtencion de pagos");
        return ResponseEntity.ok(pagoService.obtenerTotalPedidos());
    }

    @Operation(summary = "Buscar pago por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/pagos/{id}")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[Pago Controller] Iniciando obtencion de pago");
        Optional<PagoDTO> pago = pagoService.buscarPorId(id);
        return pago.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo pago", description = "Obtiene el total desde ms-pedidos via FeignClient")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/pagos")
    public ResponseEntity<PagoDTO> guardar(@Valid @RequestBody PagoDTO pagoDTO) {
        log.info("[Pago Controller] Iniciando guardar");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pagoService.guardar(pagoDTO));
    }

    @Operation(summary = "Actualizar pago existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/pagos/{id}")
    public ResponseEntity<PagoDTO> actualizar(@PathVariable Integer id,
                                              @Valid @RequestBody PagoDTO pago) {
        log.info("[Pago Controller] Iniciando actualizar");
        return pagoService.actualizarPorId(id, pago)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar pago por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/pagos/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("[Pago Controller] Iniciando eliminar");
        boolean eliminado = pagoService.eliminarPorId(id);
        if (eliminado) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Buscar pagos por monto y estado")
    @ApiResponse(responseCode = "200", description = "Lista filtrada exitosamente")
    @GetMapping("/pagos/totales")
    public ResponseEntity<List<PagoDTO>> obtenerTotales(@RequestParam Double monto,
                                                        @RequestParam Boolean aceptado) {
        log.info("[Pago Controller] Iniciando obtencion de pagos");
        return ResponseEntity.ok(pagoService.buscarPagos(monto, aceptado));
    }
}