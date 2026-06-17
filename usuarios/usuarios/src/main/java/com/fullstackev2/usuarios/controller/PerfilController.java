package com.fullstackev2.usuarios.controller;


import com.fullstackev2.usuarios.dto.PerfilDTO;
import com.fullstackev2.usuarios.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary ="Listar perfil ",description = "Obtiene todos los perfiles registrados")
    @ApiResponse(responseCode = "200",description = "Perfiles correctamente")
    @ApiResponse(responseCode = "404",description = "Perfiles no encontrados")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<List<PerfilDTO>> listarPerfiles(){
        log.info("[Perfil Controller] Iniciando listar perfiles");
        List<PerfilDTO> perfil = perfilService.obtenerPerfiles();
        return ResponseEntity.ok(perfil);
    }
    @GetMapping("/perfiles/{id}")
    @Operation(summary ="Buscar perfil por id",description = "Busca un perfil por su identificador unico")
    @ApiResponse(responseCode = "200",description = "Perfil obtenido correctamente")
    @ApiResponse(responseCode = "404",description = "Perfil no encontrado")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<PerfilDTO> buscarPorId(@PathVariable("id") Integer id){
        log.info("[Perfil Controller] Iniciando buscar perfile");
        Optional<PerfilDTO> perfil = perfilService.buscarPorId(id);
        return perfil
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/perfiles")
    @Operation(summary ="Guardar un perfil",description = "Registra un nuevo perfil en la base de datos")
    @ApiResponse(responseCode = "201",description = "Perfil creado correctamente")
    @ApiResponse(responseCode = "400",description = "Uno o mas datos invalidos")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<PerfilDTO> guardar(@Valid @RequestBody PerfilDTO dto){
        log.info("[Perfil Controller] Iniciando guardar perfile");
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.guardar(dto));}
    @PutMapping("/perfiles/{id}")
    @Operation(summary ="Actualizar perfil",description = "Actualiza los datos de un perfil registrado previamente")
    @ApiResponse(responseCode = "200",description = "Perfil actualizado correctamente")
    @ApiResponse(responseCode = "404",description = "Perfil no encontrado")
    @ApiResponse(responseCode = "400",description = "Uno o mas datos invalidos")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<PerfilDTO> actualizar(@PathVariable("id") Integer id, @Valid @RequestBody PerfilDTO dto){
        log.info("[Perfil Controller] Iniciando actualizar perfile");
        return perfilService.actualizarPorId(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());}
    @DeleteMapping("/perfiles/{id}")
    @Operation(summary ="Elimina un perfil",description = "Elimina los datos de un perfil registrado previamente")
    @ApiResponse(responseCode = "204",description = "Perfil eliminado correctamente")
    @ApiResponse(responseCode = "404",description = "Perfil no encontrado")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id){
        log.info("[Perfil Controller] Iniciando eliminar perfile");
        boolean eliminado = perfilService.eliminarPorId(id);
        if(eliminado) {return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
}}
