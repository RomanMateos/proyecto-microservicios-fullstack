package com.fullstackev2.inventario.mapper;

import com.fullstackev2.inventario.dto.MovimientoStockDTO;
import com.fullstackev2.inventario.model.MovimientoStock;

public class MovimientoStockMapper {
    public static MovimientoStockDTO toDTO(MovimientoStock movimiento){
        return new MovimientoStockDTO(
                movimiento.getId(),
                movimiento.getTipoMovimiento(),
                movimiento.getMotivo(),
                movimiento.getCantidad(),
                movimiento.getAprobado(),
                movimiento.getFechaMovimiento(),
                movimiento.getInventario() != null ? movimiento.getInventario().getId():null

        );

    }
    public static MovimientoStock toEntity(MovimientoStockDTO dto){
        MovimientoStock movimientoStock = new MovimientoStock();
        movimientoStock.setId(dto.getId());
        movimientoStock.setTipoMovimiento(dto.getTipoMovimiento());
        movimientoStock.setMotivo(dto.getMotivo());
        movimientoStock.setCantidad(dto.getCantidad());
        movimientoStock.setAprobado(dto.getAprobado());
        movimientoStock.setFechaMovimiento(dto.getFechaMovimiento());

        return movimientoStock;
    }
}
