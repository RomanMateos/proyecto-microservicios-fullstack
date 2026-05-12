package com.fullstackev2.usuarios.controller;


import com.fullstackev2.usuarios.dto.UsuarioDTO;

import com.fullstackev2.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<UsuarioDTO> listarUsuarios(){ return usuarioService.obtenerUsuarios();}
    @GetMapping("/usuarios/{id}")
    public UsuarioDTO obtenerUsuario(@PathVariable Integer id){ return usuarioService.buscarPorId(id);}
    @PostMapping("/usuarios")
    public UsuarioDTO guardar(@RequestBody UsuarioDTO dto){ return usuarioService.guardar(dto); }
    @PutMapping("/usuarios/{id}")
    public UsuarioDTO actualizar(@PathVariable Integer id, @RequestBody UsuarioDTO dto){return usuarioService.actualizarPorId(id, dto);}
    @DeleteMapping("/usuarios/{id}")
    public void eliminar(@PathVariable Integer id){ usuarioService.eliminarPorId(id);}
}
