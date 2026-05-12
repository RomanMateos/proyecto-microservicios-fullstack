package com.fullstackev2.usuarios.controller;


import com.fullstackev2.usuarios.dto.UsuarioDTO;

import com.fullstackev2.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(){
        List<UsuarioDTO> user = usuarioService.obtenerUsuarios();
        return ResponseEntity.ok(user);}
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Integer id){
        Optional<UsuarioDTO> usuario = usuarioService.buscarPorId(id);
        return usuario
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());}
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> guardar(@Valid @RequestBody UsuarioDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(dto)); }
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO dto){
        return usuarioService.actualizarPorId(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());}
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        boolean eliminado = usuarioService.eliminarPorId(id);
        if(eliminado) {return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/usuarios/buscar")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarioPorEmail(@RequestParam String email, @RequestParam boolean activo){
        List<UsuarioDTO> usuarios = usuarioService.buscarPorEmailYActivo(email,activo);
        return ResponseEntity.ok(usuarios);
    }
}
