package com.fullstackev2.ms_pedidos.mapper;

import com.fullstackev2.ms_pedidos.dto.PedidoDTO;
import com.fullstackev2.ms_pedidos.dto.PedidoRequestDTO;
import com.fullstackev2.ms_pedidos.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setEstado(pedido.getEstado());
        dto.setDireccionEntrega(pedido.getDireccionEntrega());
        dto.setTotal(pedido.getTotal());
        dto.setPagado(pedido.isPagado());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setUsuarioId(pedido.getUsuarioId());
        return dto;
    }

    public Pedido toEntity(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setEstado(dto.getEstado());
        pedido.setDireccionEntrega(dto.getDireccionEntrega());
        pedido.setTotal(dto.getTotal());
        pedido.setPagado(dto.isPagado());
        pedido.setFechaPedido(dto.getFechaPedido());
        pedido.setUsuarioId(dto.getUsuarioId());
        return pedido;
    }
}