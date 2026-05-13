package com.fullstackev2.envios.mapper;

import com.fullstackev2.envios.dto.EnvioDTO;
import com.fullstackev2.envios.model.Envio;

public class EnvioMapper {
    public static EnvioDTO toDTO(Envio envio){
        return new EnvioDTO(
                envio.getId(),
                envio.getDireccionEnvio(),
                envio.getNroSeguimiento(),
                envio.getCostoEnvio(),
                envio.getActivo(),
                envio.getFechaEnvio(),
                envio.getPedidoId(),
                envio.getUsuarioId()

        );
    }
    public static Envio toEntity(EnvioDTO envioDTO){
        Envio envio = new Envio();
        envio.setId( envioDTO.getId() );
        envio.setDireccionEnvio( envioDTO.getDireccionEnvio() );
        envio.setNroSeguimiento( envioDTO.getNroSeguimiento() );
        envio.setCostoEnvio( envioDTO.getCostoEnvio() );
        envio.setActivo( envioDTO.getActivo() );
        envio.setFechaEnvio( envioDTO.getFechaEnvio() );
        envio.setPedidoId( envioDTO.getPedidoId() );
        envio.setUsuarioId( envioDTO.getUsuarioId() );
        return envio;

    }
}
