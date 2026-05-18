package com.fullstackev2.usuarios.service;

import com.fullstackev2.usuarios.dto.UsuarioDTO;
import com.fullstackev2.usuarios.mapper.UsuarioMapper;
import com.fullstackev2.usuarios.model.Usuario;
import com.fullstackev2.usuarios.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> obtenerUsuarios(){
        log.info("[Usuario Service] Iniciando obtenerUsuarios");
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO guardar(UsuarioDTO dto){
        log.info("[Usuario Service] Iniciando guardar usuario");
        Usuario usuario = UsuarioMapper.toEntity(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(guardado);
    }
    public Optional<UsuarioDTO> buscarPorId(Integer id){
        log.info("[Usuario Service] Iniciando buscar usuario");
        return usuarioRepository.findById(id)
                .map(UsuarioMapper::toDTO);

    }
    public Optional<UsuarioDTO> actualizarPorId(Integer id, UsuarioDTO dto){
        log.info("[Usuario Service] Iniciando actualizar usuario");
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setDireccion(dto.getDireccion());
        usuario.setEdad(dto.getEdad());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setActivo(dto.getActivo());

        Usuario actualizado = usuarioRepository.save(usuario);
        return Optional.of(UsuarioMapper.toDTO(actualizado));
    }
    public boolean eliminarPorId(Integer id){
        log.info("[Usuario Service] Iniciando eliminar usuario");
        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
}
    public List<UsuarioDTO> buscarPorEmailYActivo(String email,Boolean activo){
        log.info("[Usuario Service] Iniciando buscar usuario");
        return usuarioRepository.findByEmailAndActivo(email,activo)
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());

    }
}
