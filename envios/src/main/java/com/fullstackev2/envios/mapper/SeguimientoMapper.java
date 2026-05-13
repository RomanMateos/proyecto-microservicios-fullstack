package com.fullstackev2.envios.mapper;

import com.fullstackev2.envios.dto.SeguimientoDTO;
import com.fullstackev2.envios.model.Envio;
import com.fullstackev2.envios.model.Seguimiento;

public class SeguimientoMapper {
    public static SeguimientoDTO toDTO(Seguimiento seguimiento){
        return new SeguimientoDTO(
                seguimiento.getId(),
                seguimiento.getEstado(),
                seguimiento.getDireccionEnvio(),
                seguimiento.getFechaDespacho(),
                seguimiento.getActivo(),
                seguimiento.getAncho(),
                seguimiento.getAlto(),
                seguimiento.getEntregado(),
                seguimiento.getEnvio() != null ? seguimiento.getEnvio().getId():null
        );
    }
    public static Seguimiento toEntity(SeguimientoDTO seguimientoDTO){
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setEstado(seguimientoDTO.getEstado());
        seguimiento.setDireccionEnvio(seguimientoDTO.getDireccionEnvio());
        seguimiento.setFechaDespacho(seguimientoDTO.getFechaDespacho());
        seguimiento.setActivo(seguimientoDTO.getActivo());
        seguimiento.setAncho(seguimientoDTO.getAncho());
        seguimiento.setAlto(seguimientoDTO.getAlto());
        seguimiento.setEntregado(seguimientoDTO.getEntregado());

        if(seguimientoDTO.getEnvioId() != null){
            Envio envio = new Envio();
            envio.setId(seguimientoDTO.getEnvioId());
            seguimiento.setEnvio(envio);
        }
        return seguimiento;
    }
}
