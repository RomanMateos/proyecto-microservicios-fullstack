package com.fullstackev2.usuarios.controller;


import com.fullstackev2.usuarios.dto.PerfilDTO;
import com.fullstackev2.usuarios.service.PerfilService;
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
public class PerfilController {
    @Autowired
    private PerfilService perfilService;
    @GetMapping("/perfiles")
    public ResponseEntity<List<PerfilDTO>> listarPerfiles(){
        List<PerfilDTO> perfil = perfilService.obtenerPerfiles();
        return ResponseEntity.ok(perfil);
    }
    @GetMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> buscarPorId(@PathVariable Integer id){
        Optional<PerfilDTO> perfil = perfilService.buscarPorId(id);
        return perfil
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/perfiles")
    public ResponseEntity<PerfilDTO> guardar(@Valid @RequestBody PerfilDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.guardar(dto));}
    @PutMapping("/perfiles/{id}")
    public ResponseEntity<PerfilDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody PerfilDTO dto){
        return perfilService.actualizarPorId(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());}
    @DeleteMapping("/perfiles/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        boolean eliminado = perfilService.eliminarPorId(id);
        if(eliminado) {return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
}}
