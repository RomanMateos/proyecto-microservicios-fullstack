package com.fullstackev2.pedidos.service;

import com.fullstackev2.pedidos.client.UsuarioClient;
import com.fullstackev2.pedidos.dto.UsuarioDTO;
import com.fullstackev2.pedidos.model.Pedido;
import com.fullstackev2.pedidos.dto.PedidoDTO;
import com.fullstackev2.pedidos.mapper.PedidoMapper;
import com.fullstackev2.pedidos.repository.PedidoRepository;
import com.fullstackev2.pedidos.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UsuarioClient usuarioClient;
    public List<PedidoDTO> obtenerPedidos() {

        return pedidoRepository.findAll()
                .stream()
                .map(PedidoMapper::toDTO)
                .toList();
    }

    public PedidoDTO buscarPorId(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
        return PedidoMapper.toDTO(pedido);
    }


    public PedidoDTO guardar(PedidoDTO dto) {

        UsuarioDTO usuario = usuarioClient.getUsuarioById(dto.getUsuarioId());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (usuario.getActivo() == null || !usuario.getActivo()) {
            throw new RuntimeException("Usuario no activo");
        }
        Pedido pedido = PedidoMapper.toEntity(dto);
        if (pedido.getTotal() == null) {
            pedido.setTotal(0.0);
        }
        if (pedido.getPagado() == null) {
            pedido.setPagado(false);
        }
        Pedido guardado = pedidoRepository.save(pedido);
        return PedidoMapper.toDTO(guardado);
    }

    public Optional<PedidoDTO> actualizarPorId(Integer id, PedidoDTO dto) {
        pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        Pedido pedido = PedidoMapper.toEntity(dto);
        pedido.setId(id);
        Pedido actualizado = pedidoRepository.save(pedido);
        return Optional.of(PedidoMapper.toDTO(actualizado));
    }

    public Boolean eliminarPorId(Integer id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Double obtenerTotalPedido(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido no encontrado con id: " + id));

        return pedido.getTotal();
    }
}