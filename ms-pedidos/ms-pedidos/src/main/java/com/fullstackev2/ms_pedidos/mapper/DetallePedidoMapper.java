package com.fullstackev2.ms_pedidos.mapper;

import com.fullstackev2.ms_pedidos.dto.DetallePedidoDTO;
import com.fullstackev2.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.fullstackev2.ms_pedidos.model.DetallePedido;
import org.springframework.stereotype.Component;

@Component
public class DetallePedidoMapper {

    public DetallePedidoDTO toDTO(DetallePedido d) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(d.getId());
        dto.setNombreProducto(d.getNombreProducto());
        dto.setCantidad(d.getCantidad());
        dto.setPrecioUnitario(d.getPrecioUnitario());
        dto.setActivo(d.isActivo());
        dto.setFechaAgregado(d.getFechaAgregado());
        dto.setProductoId(d.getProductoId());
        if (d.getPedido() != null) dto.setPedidoId(d.getPedido().getId());
        return dto;
    }

    public DetallePedido toEntity(DetallePedidoRequestDTO dto) {
        DetallePedido d = new DetallePedido();
        d.setNombreProducto(dto.getNombreProducto());
        d.setCantidad(dto.getCantidad());
        d.setPrecioUnitario(dto.getPrecioUnitario());
        d.setActivo(dto.isActivo());
        d.setFechaAgregado(dto.getFechaAgregado());
        d.setProductoId(dto.getProductoId());
        return d;
    }
}