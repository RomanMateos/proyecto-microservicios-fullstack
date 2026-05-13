package com.fullstackev2.ms_pedidos.service;

import com.fullstackev2.ms_pedidos.dto.PedidoDTO;
import com.fullstackev2.ms_pedidos.dto.PedidoRequestDTO;
import com.fullstackev2.ms_pedidos.exception.ResourceNotFoundException;
import com.fullstackev2.ms_pedidos.mapper.PedidoMapper;
import com.fullstackev2.ms_pedidos.model.Pedido;
import com.fullstackev2.ms_pedidos.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public List<PedidoDTO> findAll() {
        log.info("Obteniendo todos los pedidos");
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO findById(Integer id) {
        log.info("Buscando pedido con id: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido no encontrado con id: " + id));
        return pedidoMapper.toDTO(pedido);
    }

    public PedidoDTO save(PedidoRequestDTO dto) {
        log.info("Guardando nuevo pedido");
        Pedido pedido = pedidoMapper.toEntity(dto);
        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    public PedidoDTO update(Integer id, PedidoRequestDTO dto) {
        log.info("Actualizando pedido con id: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido no encontrado con id: " + id));
        pedido.setEstado(dto.getEstado());
        pedido.setDireccionEntrega(dto.getDireccionEntrega());
        pedido.setTotal(dto.getTotal());
        pedido.setPagado(dto.isPagado());
        pedido.setFechaPedido(dto.getFechaPedido());
        pedido.setUsuarioId(dto.getUsuarioId());
        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    public void delete(Integer id) {
        log.info("Eliminando pedido con id: {}", id);
        try {
            pedidoRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error al eliminar pedido con id: {}", id, e);
            throw new ResourceNotFoundException(
                    "Pedido no encontrado con id: " + id);
        }
    }
}