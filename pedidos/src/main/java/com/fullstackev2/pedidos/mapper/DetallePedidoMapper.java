package com.fullstackev2.pedidos.mapper;

import com.fullstackev2.pedidos.dto.DetallePedidoDTO;
import com.fullstackev2.pedidos.dto.DetallePedidoRequestDTO;
import com.fullstackev2.pedidos.model.DetallePedido;
import org.springframework.stereotype.Component;

@Component
public class DetallePedidoMapper {

    public static DetallePedidoDTO toDTO(DetallePedido d) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(d.getId());
        dto.setNombreProducto(d.getNombreProducto());
        dto.setCantidad(d.getCantidad());
        dto.setPrecioUnitario(d.getPrecioUnitario());
        dto.setActivo(d.getActivo());
        dto.setFechaAgregado(d.getFechaAgregado());
        dto.setProductoId(d.getProductoId());
        if (d.getPedido() != null) dto.setPedidoId(d.getPedido().getId());
        return dto;
    }

    public static DetallePedido toEntity(DetallePedidoDTO dto) {
        DetallePedido d = new DetallePedido();
        d.setNombreProducto(dto.getNombreProducto());
        d.setCantidad(dto.getCantidad());
        d.setPrecioUnitario(dto.getPrecioUnitario());
        d.setActivo(dto.getActivo());
        d.setFechaAgregado(dto.getFechaAgregado());
        d.setProductoId(dto.getProductoId());
        return d;
    }
}