package com.fullstackev2.pagos.controller;


import com.fullstackev2.pagos.dto.PagoDTO;
import com.fullstackev2.pagos.service.PagoService;
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
public class PagoController {
    @Autowired
    private PagoService pagoService;
    @GetMapping("/pagos")
    public ResponseEntity<List<PagoDTO>> listarPagos(){
        log.info("[Pago Controller] Iniciando obtencion de pagos");
        List<PagoDTO> pago = pagoService.obtenerTotalPedidos();
        return ResponseEntity.ok().body(pago);
    }
    @GetMapping("/pagos/{id}")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable Integer id){
        log.info("[Pago Controller] Iniciando obtencion de pago");
        Optional<PagoDTO> pago = pagoService.buscarPorId(id);
        return pago
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/pagos")
    public ResponseEntity<PagoDTO> guardar(@Valid @RequestBody PagoDTO pagoDTO){
        log.info("[Pago Controller] Iniciando guardar");
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardar(pagoDTO));
    }
    @PutMapping("/pagos/{id}")
    public ResponseEntity<PagoDTO> actualizar(@PathVariable Integer id,@Valid @RequestBody PagoDTO pago){
        log.info("[Pago Controller] Iniciando actualizar");
        return pagoService.actualizarPorId(id,pago)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/pagos/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Integer id){
        log.info("[Pago Controller] Iniciando eliminar");
        boolean eliminado = pagoService.eliminarPorId(id);
        if(eliminado){return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/pagos/totales")
    public ResponseEntity<List<PagoDTO>> obtenerTotales(@RequestParam Double monto, @RequestParam Boolean aceptado){
        log.info("[Pago Controller] Iniciando obtencion de pagos");
        List<PagoDTO> pagos = pagoService.buscarPagos(monto,aceptado);
        return ResponseEntity.ok(pagos);
    }
}
