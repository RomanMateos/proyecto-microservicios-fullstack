package com.fullstackev2.usuarios.service;

import com.fullstackev2.usuarios.dto.UsuarioDTO;
import com.fullstackev2.usuarios.mapper.UsuarioMapper;
import com.fullstackev2.usuarios.model.Usuario;
import com.fullstackev2.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> obtenerUsuarios(){
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO guardar(UsuarioDTO dto){
        Usuario usuario = UsuarioMapper.toEntity(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(guardado);
    }
    public Optional<UsuarioDTO> buscarPorId(Integer id){
        return usuarioRepository.findById(id)
                .map(UsuarioMapper::toDTO);

    }
    public Optional<UsuarioDTO> actualizarPorId(Integer id, UsuarioDTO dto){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setDireccion(dto.getDireccion());
        usuario.setEdad(dto.getEdad());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setActivo(dto.isActivo());

        Usuario actualizado = usuarioRepository.save(usuario);
        return Optional.of(UsuarioMapper.toDTO(actualizado));
    }
    public boolean eliminarPorId(Integer id){
        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;


}
}
