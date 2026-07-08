package com.fullstackev2.pedidos.service;

import com.fullstackev2.pedidos.client.ProductoClient;
import com.fullstackev2.pedidos.client.UsuarioClient;
import com.fullstackev2.pedidos.dto.ProductoDTO;
import com.fullstackev2.pedidos.model.DetallePedido;
import com.fullstackev2.pedidos.dto.DetallePedidoDTO;
import com.fullstackev2.pedidos.dto.DetallePedidoRequestDTO;
import com.fullstackev2.pedidos.mapper.DetallePedidoMapper;
import com.fullstackev2.pedidos.model.Pedido;
import com.fullstackev2.pedidos.repository.DetallePedidoRepository;
import com.fullstackev2.pedidos.exception.ResourceNotFoundException;
import com.fullstackev2.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoClient productoClient;
    @Autowired
    private UsuarioClient usuarioClient;

    public List<DetallePedidoDTO> listarDetallePedido() {
        return detallePedidoRepository.findAll()
                .stream()
                .map(DetallePedidoMapper::toDTO)
                .collect(Collectors.toList());

    }

    public Optional<DetallePedidoDTO> buscarPorId(Integer id) {
        return detallePedidoRepository.findById(id)
                .map(DetallePedidoMapper::toDTO);
    }

    public DetallePedidoDTO guardar(DetallePedidoRequestDTO dto) {

        ProductoDTO producto = productoClient.getProductoById(dto.getProductoId());

        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a cero");
        }

        DetallePedido detalle = new DetallePedido();

        detalle.setPedido(pedido);
        detalle.setProductoId(producto.getId());
        detalle.setNombreProducto(producto.getNombreProducto());
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setCantidad(dto.getCantidad());
        detalle.setActivo(true);
        detalle.setFechaAgregado(LocalDate.now());

        DetallePedido guardado = detallePedidoRepository.save(detalle);

        recalcularTotalPedido(pedido);

        return DetallePedidoMapper.toDTO(guardado);
    }


    public DetallePedidoDTO actualizarPorId(Integer id, DetallePedidoRequestDTO dto) {

        DetallePedido detalle = detallePedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "DetallePedido no encontrado con id: " + id));

        ProductoDTO producto = productoClient.getProductoById(dto.getProductoId());
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() ->
                        new RuntimeException("Pedido no encontrado"));
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a cero");
        }
        detalle.setPedido(pedido);
        detalle.setProductoId(producto.getId());
        detalle.setNombreProducto(producto.getNombreProducto());
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setCantidad(dto.getCantidad());
        detalle.setActivo(true);
        detalle.setFechaAgregado(LocalDate.now());

        DetallePedido actualizado = detallePedidoRepository.save(detalle);

        recalcularTotalPedido(pedido);

        return DetallePedidoMapper.toDTO(actualizado);
    }

    public Boolean eliminarPorId(Integer id) {
        Optional<DetallePedido> detalleOpt = detallePedidoRepository.findById(id);
        if (detalleOpt.isEmpty()) {return false;}
        DetallePedido detalle = detalleOpt.get();
        Pedido pedido = detalle.getPedido();
        detallePedidoRepository.deleteById(id);
        if (pedido != null) {recalcularTotalPedido(pedido);}
        return true;
    }

    private void recalcularTotalPedido(Pedido pedido) {

        Double total = detallePedidoRepository.findAll()
                .stream()
                .filter(detalle -> detalle.getPedido() != null)
                .filter(detalle -> detalle.getPedido().getId().equals(pedido.getId()))
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();

        pedido.setTotal(total);

        pedidoRepository.save(pedido);
    }
}