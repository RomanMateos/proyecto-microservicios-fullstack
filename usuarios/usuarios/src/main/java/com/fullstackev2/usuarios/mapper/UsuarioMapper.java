package com.fullstackev2.usuarios.mapper;

import com.fullstackev2.usuarios.dto.UsuarioDTO;
import com.fullstackev2.usuarios.model.Usuario;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                usuario.getDireccion(),
                usuario.getEdad(),
                usuario.getFechaNacimiento(),
                usuario.getActivo()

        );
    }
    public static Usuario toEntity(UsuarioDTO dto) {
            Usuario usuario = new Usuario();

            usuario.setId(dto.getId());
            usuario.setNombreCompleto(dto.getNombreCompleto());
            usuario.setEmail(dto.getEmail());
            usuario.setDireccion(dto.getDireccion());
            usuario.setEdad(dto.getEdad());
            usuario.setFechaNacimiento(dto.getFechaNacimiento());
            usuario.setActivo(dto.getActivo());

            return usuario;

    }

}

