package com.fullstackev2.usuarios.controller;


import com.fullstackev2.usuarios.dto.PerfilDTO;
import com.fullstackev2.usuarios.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class PerfilController {
    @Autowired
    private PerfilService perfilService;
    @GetMapping("/perfiles")
    public List<PerfilDTO> perfiles(){ return perfilService.obtenerPerfiles();}
    @GetMapping("/perfiles/{id}")
    public PerfilDTO buscarPorId(@PathVariable Integer id){ return perfilService.buscarPorId(id);}
    @PostMapping("/perfiles")
    public PerfilDTO perfil(@RequestBody PerfilDTO dto){ return perfilService.guardar(dto);}
    @PutMapping("/perfiles/{id}")
    public PerfilDTO actualizar(@PathVariable Integer id, @RequestBody PerfilDTO dto){ return perfilService.actualizarPorId(id,dto);}
    @DeleteMapping("/perfiles/{id}")
    public void eliminar(@PathVariable Integer id){ perfilService.eliminarPorId(id);}
}
