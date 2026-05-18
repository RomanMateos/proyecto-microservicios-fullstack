package com.fullstackev2.usuarios.service;

import com.fullstackev2.usuarios.dto.PerfilDTO;
import com.fullstackev2.usuarios.mapper.PerfilMapper;
import com.fullstackev2.usuarios.model.Perfil;
import com.fullstackev2.usuarios.model.Usuario;
import com.fullstackev2.usuarios.repository.PerfilRepository;
import com.fullstackev2.usuarios.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public List<PerfilDTO> obtenerPerfiles() {
        log.info("[Perfil Service] Iniciando obtencion de perfiles");
        return perfilRepository.findAll()
                .stream()
                .map(PerfilMapper::toDTO)
                .collect(Collectors.toList());
    }
    public PerfilDTO guardar(PerfilDTO dto){
        log.info("[Perfil Service] Iniciando guardar perfile");
        Perfil perfil = PerfilMapper.toEntity(dto);
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        perfil.setUsuario(usuario);
        Perfil guardado = perfilRepository.save(perfil);
        return PerfilMapper.toDTO(guardado);
    }
    public Optional<PerfilDTO> buscarPorId(Integer id){
        log.info("[Perfil Service] Iniciando buscar perfile");
        return perfilRepository.findById(id)
                .map(PerfilMapper::toDTO);
    }
    public  Optional<PerfilDTO> actualizarPorId(Integer id, PerfilDTO dto){
        log.info("[Perfil Service] Iniciando actualizar perfile");
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el perfil com este id"));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        perfil.setNombrePerfil(dto.getNombrePerfil());
        perfil.setAlias(dto.getAlias());
        perfil.setEmail(dto.getEmail());
        perfil.setEdad(dto.getEdad());
        perfil.setFechaNacimiento(dto.getFechaNacimiento());
        perfil.setActivo(dto.getActivo());
        perfil.setUsuario(usuario);
        Perfil actualizado = perfilRepository.save(perfil);
        return Optional.of(PerfilMapper.toDTO(actualizado));
    }
    public boolean eliminarPorId(Integer id){
        log.info("[Perfil Service] Iniciando eliminar perfile");
        if(perfilRepository.existsById(id)) {
            perfilRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
