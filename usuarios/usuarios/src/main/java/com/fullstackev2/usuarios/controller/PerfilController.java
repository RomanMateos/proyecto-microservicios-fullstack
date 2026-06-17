package com.fullstackev2.usuarios.controller;


import com.fullstackev2.usuarios.dto.PerfilDTO;
import com.fullstackev2.usuarios.service.PerfilService;
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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("api/v1")
@Tag(name= "Perfiles", description = "Operaciones CRUD de perfiles")
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
}
    @GetMapping("/perfiles/{id}/hateoas")
    @Operation(
            summary = "Buscar perfil por ID con HATEOAS",
            description = "Obtiene un perfil específico y agrega enlaces relacionados mediante HATEOAS."
    )
    @ApiResponse(responseCode = "200", description = "Perfil encontrado con enlaces HATEOAS")
    @ApiResponse(responseCode = "404", description = "Perfil no encontrado")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<EntityModel<PerfilDTO>> buscarPorIdHateoas(@PathVariable("id") Integer id) {
        log.info("[Perfil Controller] Iniciando buscarPorIdHateoas");

        Optional<PerfilDTO> perfil = perfilService.buscarPorId(id);

        if (perfil.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<PerfilDTO> recurso = EntityModel.of(perfil.get());

        recurso.add(linkTo(methodOn(PerfilController.class).buscarPorId(id)).withSelfRel());
        recurso.add(linkTo(methodOn(PerfilController.class).listarPerfiles()).withRel("perfiles"));
        recurso.add(linkTo(methodOn(PerfilController.class).eliminar(id)).withRel("eliminar"));

        return ResponseEntity.ok(recurso);
    }
    @GetMapping("/perfiles/hateoas")
    @Operation(
            summary = "Listar perfiles con HATEOAS",
            description = "Obtiene todos los perfiles registrados y agrega enlaces HATEOAS a cada perfil."
    )
    @ApiResponse(responseCode = "200", description = "Perfiles obtenidos con enlaces HATEOAS")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<CollectionModel<EntityModel<PerfilDTO>>> listarPerfilesHateoas() {
        log.info("[Perfil Controller] Iniciando listarPerfilesHateoas");

        List<EntityModel<PerfilDTO>> perfiles = perfilService.obtenerPerfiles()
                .stream()
                .map(perfil -> {
                    EntityModel<PerfilDTO> recurso = EntityModel.of(perfil);

                    recurso.add(linkTo(methodOn(PerfilController.class)
                            .buscarPorId(perfil.getId())).withSelfRel());

                    recurso.add(linkTo(methodOn(PerfilController.class)
                            .buscarPorIdHateoas(perfil.getId())).withRel("hateoas"));

                    recurso.add(linkTo(methodOn(PerfilController.class)
                            .eliminar(perfil.getId())).withRel("eliminar"));

                    return recurso;
                })
                .toList();

        CollectionModel<EntityModel<PerfilDTO>> coleccion = CollectionModel.of(perfiles);

        coleccion.add(linkTo(methodOn(PerfilController.class).listarPerfiles()).withRel("perfiles"));
        coleccion.add(linkTo(methodOn(PerfilController.class).listarPerfilesHateoas()).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }

}
