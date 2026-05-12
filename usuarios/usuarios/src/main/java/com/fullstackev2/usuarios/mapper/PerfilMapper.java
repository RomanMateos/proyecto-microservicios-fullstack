package com.fullstackev2.usuarios.mapper;

import com.fullstackev2.usuarios.dto.PerfilDTO;
import com.fullstackev2.usuarios.model.Perfil;


public class PerfilMapper {
    public static PerfilDTO toDTO(Perfil perfil) {
        return new PerfilDTO(
                perfil.getId(),
                perfil.getNombrePerfil(),
                perfil.getAlias(),
                perfil.getEmail(),
                perfil.getEdad(),
                perfil.getFechaNacimiento(),
                perfil.getActivo(),
                perfil.getUsuario() != null ? perfil.getUsuario().getId(): null
        );
    }
    public static Perfil toEntity(PerfilDTO perfilDTO) {
        Perfil perfil = new Perfil();

        perfil.setId(perfilDTO.getId());
        perfil.setNombrePerfil(perfilDTO.getNombrePerfil());
        perfil.setAlias(perfilDTO.getAlias());
        perfil.setEmail(perfilDTO.getEmail());
        perfil.setEdad(perfilDTO.getEdad());
        perfil.setFechaNacimiento(perfilDTO.getFechaNacimiento());
        perfil.setActivo(perfilDTO.getActivo());

        return perfil;
    }

}

