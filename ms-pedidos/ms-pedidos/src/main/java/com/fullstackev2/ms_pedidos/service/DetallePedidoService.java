package com.fullstackev2.ms_pedidos.service;

import com.fullstackev2.ms_pedidos.model.DetallePedido;
import com.fullstackev2.ms_pedidos.dto.DetallePedidoDTO;
import com.fullstackev2.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.fullstackev2.ms_pedidos.mapper.DetallePedidoMapper;
import com.fullstackev2.ms_pedidos.repository.DetallePedidoRepository;
import com.fullstackev2.ms_pedidos.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private DetallePedidoMapper detallePedidoMapper;

    public List<DetallePedidoDTO> findAll() {
        return detallePedidoRepository.findAll()
                .stream()
                .map(detallePedidoMapper::toDTO)
                .toList();
    }

    public DetallePedidoDTO findById(Integer id) {
        DetallePedido detalle = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DetallePedido no encontrado con id: " + id));
        return detallePedidoMapper.toDTO(detalle);
    }

    public DetallePedidoDTO save(DetallePedidoRequestDTO requestDTO) {
        DetallePedido detalle = detallePedidoMapper.toEntity(requestDTO);
        return detallePedidoMapper.toDTO(detallePedidoRepository.save(detalle));
    }

    public DetallePedidoDTO update(Integer id, DetallePedidoRequestDTO requestDTO) {
        detallePedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DetallePedido no encontrado con id: " + id));
        DetallePedido detalle = detallePedidoMapper.toEntity(requestDTO);
        detalle.setId(id); // asegura que actualiza el mismo registro
        return detallePedidoMapper.toDTO(detallePedidoRepository.save(detalle));
    }

    public void delete(Integer id) {
        detallePedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DetallePedido no encontrado con id: " + id));
        detallePedidoRepository.deleteById(id);
    }
}