package com.fullstackev2.ms_pedidos.service;

import com.fullstackev2.ms_pedidos.dto.DetallePedidoDTO;
import com.fullstackev2.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.fullstackev2.ms_pedidos.exception.ResourceNotFoundException;
import com.fullstackev2.ms_pedidos.mapper.DetallePedidoMapper;
import com.fullstackev2.ms_pedidos.model.DetallePedido;
import com.fullstackev2.ms_pedidos.model.Pedido;
import com.fullstackev2.ms_pedidos.repository.DetallePedidoRepository;
import com.fullstackev2.ms_pedidos.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePedidoService {

    private static final Logger log = LoggerFactory.getLogger(DetallePedidoService.class);

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoMapper detallePedidoMapper;

    public List<DetallePedidoDTO> findAll() {
        log.info("Obteniendo todos los detalles de pedido");
        return detallePedidoRepository.findAll()
                .stream()
                .map(detallePedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DetallePedidoDTO findById(Integer id) {
        log.info("Buscando detalle pedido con id: {}", id);
        DetallePedido d = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DetallePedido no encontrado con id: " + id));
        return detallePedidoMapper.toDTO(d);
    }

    public DetallePedidoDTO save(DetallePedidoRequestDTO dto) {
        log.info("Guardando nuevo detalle pedido");
        DetallePedido d = detallePedidoMapper.toEntity(dto);
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido no encontrado con id: " + dto.getPedidoId()));
        d.setPedido(pedido);
        return detallePedidoMapper.toDTO(detallePedidoRepository.save(d));
    }

    public DetallePedidoDTO update(Integer id, DetallePedidoRequestDTO dto) {
        log.info("Actualizando detalle pedido con id: {}", id);
        DetallePedido d = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "DetallePedido no encontrado con id: " + id));
        d.setNombreProducto(dto.getNombreProducto());
        d.setCantidad(dto.getCantidad());
        d.setPrecioUnitario(dto.getPrecioUnitario());
        d.setActivo(dto.isActivo());
        d.setFechaAgregado(dto.getFechaAgregado());
        d.setProductoId(dto.getProductoId());
        return detallePedidoMapper.toDTO(detallePedidoRepository.save(d));
    }

    public void delete(Integer id) {
        log.info("Eliminando detalle pedido con id: {}", id);
        try {
            detallePedidoRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error al eliminar detalle pedido con id: {}", id, e);
            throw new ResourceNotFoundException(
                    "DetallePedido no encontrado con id: " + id);
        }
    }
}