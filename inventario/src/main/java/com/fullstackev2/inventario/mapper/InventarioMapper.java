package com.fullstackev2.inventario.mapper;

import com.fullstackev2.inventario.dto.InventarioDTO;
import com.fullstackev2.inventario.model.Inventario;

public class InventarioMapper {
    public static InventarioDTO toDTO(Inventario inventario){
        return new InventarioDTO(
                inventario.getId(),
                inventario.getSeccion(),
                inventario.getCantidadDisponible(),
                inventario.getUbicacion(),
                inventario.getActivo(),
                inventario.getFechaActualizacion(),
                inventario.getProductoId()
        );
    }
    public static Inventario toEntity(InventarioDTO inventarioDTO){
        Inventario inventario = new Inventario();
        inventario.setId(inventarioDTO.getId());
        inventario.setSeccion(inventarioDTO.getSeccion());
        inventario.setCantidadDisponible(inventarioDTO.getCantidadDisponible());
        inventario.setUbicacion(inventarioDTO.getUbicacion());
        inventario.setActivo(inventarioDTO.getActivo());
        inventario.setFechaActualizacion(inventarioDTO.getFechaActualizacion());
        inventario.setProductoId(inventarioDTO.getProductoId());

        return inventario;
    }
}
