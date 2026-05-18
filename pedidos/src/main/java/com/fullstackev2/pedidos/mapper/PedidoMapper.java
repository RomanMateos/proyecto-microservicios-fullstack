package com.fullstackev2.pedidos.mapper;

import com.fullstackev2.pedidos.dto.PedidoDTO;
import com.fullstackev2.pedidos.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public static PedidoDTO toDTO(Pedido pedido) {
       return new PedidoDTO(
                pedido.getId(),
                pedido.getEstado(),
                pedido.getDireccionEntrega(),
                pedido.getTotal(),
                pedido.getPagado(),
                pedido.getFechaPedido(),
                pedido.getUsuarioId()
        );
    }

    public static Pedido toEntity(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setEstado(dto.getEstado());
        pedido.setDireccionEntrega(dto.getDireccionEntrega());
        pedido.setTotal(dto.getTotal());
        pedido.setPagado(dto.getPagado());
        pedido.setFechaPedido(dto.getFechaPedido());
        pedido.setUsuarioId(dto.getUsuarioId());
        return pedido;
    }
}