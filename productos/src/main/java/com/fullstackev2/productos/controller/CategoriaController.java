package com.fullstackev2.productos.controller;

import com.fullstackev2.productos.dto.CategoriaDTO;
import com.fullstackev2.productos.service.CategoriaService;
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
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;
    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias(){
        log.info("[Categoria Controller] Iniciando buscar categorias");
        List<CategoriaDTO> categoria = categoriaService.obtenerCategorias();
        return ResponseEntity.ok().body(categoria);
    }
    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Integer id){
        log.info("[Categoria Controller] Iniciando buscar por id");
        Optional<CategoriaDTO> categoria = categoriaService.buscarPorId(id);
        return categoria
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/categorias")
    public ResponseEntity<CategoriaDTO> guardar(@Valid @RequestBody CategoriaDTO categoriaDTO){
        log.info("[Categoria Controller] Iniciando guardar o categoria");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.guardar(categoriaDTO));
    }
    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO categoriaDTO){
        log.info("[Categoria Controller] Iniciando actualizar o categoria");
        return categoriaService.actualizarPorId(id,categoriaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        log.info("[Categoria Controller] Iniciando eliminar o categoria");
        boolean eliminado = categoriaService.eliminarPorId(id);
        if(eliminado){return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
    }


}
