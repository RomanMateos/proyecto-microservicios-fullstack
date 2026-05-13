package com.fullstackev2.envios.controller;

import com.fullstackev2.envios.dto.EnvioDTO;
import com.fullstackev2.envios.service.EnvioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EnvioController {
    private EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping("/envios")
    public ResponseEntity<List<EnvioDTO>> obtenerPedidos(){
        List<EnvioDTO> envios = envioService.listarEnvio();
        return ResponseEntity.ok().body(envios);
    }
    @GetMapping("/envios/{id}")
    public ResponseEntity<EnvioDTO> buscarPorId(@PathVariable Integer id){
        Optional<EnvioDTO> envio = envioService.buscarPorId(id);
        return envio
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/envios")
    public ResponseEntity<EnvioDTO> guardar(@Valid @RequestBody EnvioDTO envioDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.guardar(envioDTO));
    }
    @PutMapping("/envios/{id}")
    public ResponseEntity<EnvioDTO> actualizar(@PathVariable Integer id,@Valid @RequestBody EnvioDTO envioDTO){
        return envioService.actualizarPorId(id,envioDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/envios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        boolean eliminado = envioService.eliminarPorId(id);
        if(eliminado){return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/envios/no-entregados")
    public ResponseEntity<List<EnvioDTO>> buscarNoEntregadosPorRango(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
    ) {
        List<EnvioDTO> envios = envioService.buscarNoEntregadosPorRango(fechaInicio, fechaFin);
        return ResponseEntity.ok(envios);
    }

}
