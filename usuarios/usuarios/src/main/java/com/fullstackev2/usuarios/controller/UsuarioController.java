package com.fullstackev2.usuarios.controller;


import com.fullstackev2.usuarios.dto.UsuarioDTO;

import com.fullstackev2.usuarios.service.UsuarioService;
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

import java.util.Optional;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("api/v1")
@Tag(name= "Usuarios", description = "Operaciones CRUD de usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    @Operation(summary ="Listar usuarios",description = "Obtiene todos los usuarios registrados")
    @ApiResponse(responseCode = "200",description = "Usuarios obtenidos correctamente")
    @ApiResponse(responseCode = "404",description = "Usuarios no encontrado")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(){
        log.info("[Usuario Controller] Iniciando listar usuarios");
        List<UsuarioDTO> user = usuarioService.obtenerUsuarios();
        return ResponseEntity.ok(user);}

    @GetMapping("/usuarios/{id}")
    @Operation(summary ="Buscar usuario por id",description = "Obtiene un usuario por su identificador unico")
    @ApiResponse(responseCode = "200",description = "Usuarios encontrado correctamente")
    @ApiResponse(responseCode = "404",description = "Usuarios no encontrado")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable("id") Integer id){
        log.info("[Usuario Controller] Iniciando obtener usuario");
        Optional<UsuarioDTO> usuario = usuarioService.buscarPorId(id);
        return usuario
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());}
    @PostMapping("/usuarios")
    @Operation(summary ="Guardar un usuario",description = "Registra un nuevo usuario en la base de datos")
    @ApiResponse(responseCode = "201",description = "Usuarios creado correctamente")
    @ApiResponse(responseCode = "400",description = "Uno o mas datos invalidos")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<UsuarioDTO> guardar(@Valid @RequestBody UsuarioDTO dto){
        log.info("[Usuario Controller] Iniciando guardar usuario");
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(dto)); }

    @PutMapping("/usuarios/{id}")
    @Operation(summary ="Actualizar usuario",description = "Actualiza los datos de un usuario registrado previamente")
    @ApiResponse(responseCode = "200",description = "Usuarios actualizado correctamente")
    @ApiResponse(responseCode = "404",description = "Usuarios no encontrado")
    @ApiResponse(responseCode = "400",description = "Uno o mas datos invalidos")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable("id") Integer id, @Valid @RequestBody UsuarioDTO dto){
        log.info("[Usuario Controller] Iniciando actualizar usuario");
        return usuarioService.actualizarPorId(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());}

    @DeleteMapping("/usuarios/{id}")
    @Operation(summary ="Eliminar un usuario",description = "Elimina un usuario por su identificador unico que este previamente creado")
    @ApiResponse(responseCode = "204",description = "Usuario eliminado correctamente")
    @ApiResponse(responseCode = "404",description = "Usuarios no encontrado")
    @ApiResponse(responseCode = "500",description = "Error inesperado")
    public ResponseEntity<Void> eliminar(@PathVariable ("id") Integer id){
        log.info("[Usuario Controller] Iniciando eliminar usuario");
        boolean eliminado = usuarioService.eliminarPorId(id);
        if(eliminado) {return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/usuarios/buscar")
    @Operation(summary = "Buscar un usuario por email y estado", description = "Busca usuarios filtrando por su correo y estado activo")
    @ApiResponse(responseCode = "404",description = "Usuarios no encontrado")
    @ApiResponse(responseCode = "200",description = "Busqueda realizada con exito")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarioPorEmail(@RequestParam("email") String email, @RequestParam("activo") boolean activo){
        log.info("[Usuario Controller] Iniciando obtener usuario");
        List<UsuarioDTO> usuarios = usuarioService.buscarPorEmailYActivo(email,activo);
        return ResponseEntity.ok(usuarios);
    }
}
