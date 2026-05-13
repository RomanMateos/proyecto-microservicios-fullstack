package com.fullstackev2.ms_pedidos.service;

import com.fullstackev2.ms_pedidos.model.Pedido;
import com.fullstackev2.ms_pedidos.dto.PedidoDTO;
import com.fullstackev2.ms_pedidos.dto.PedidoRequestDTO;
import com.fullstackev2.ms_pedidos.mapper.PedidoMapper;
import com.fullstackev2.ms_pedidos.repository.PedidoRepository;
import com.fullstackev2.ms_pedidos.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public List<PedidoDTO> findAll() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toDTO)
                .toList();
    }

    public PedidoDTO findById(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        return pedidoMapper.toDTO(pedido);
    }

    public PedidoDTO save(PedidoRequestDTO requestDTO) {
        Pedido pedido = pedidoMapper.toEntity(requestDTO);
        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    public PedidoDTO update(Integer id, PedidoRequestDTO requestDTO) {
        pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        Pedido pedido = pedidoMapper.toEntity(requestDTO);
        pedido.setId(id);
        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    public void delete(Integer id) {
        pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        pedidoRepository.deleteById(id);
    }
}