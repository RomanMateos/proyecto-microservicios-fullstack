package com.fullstackev2.inventario.service;

import com.fullstackev2.inventario.dto.MovimientoStockDTO;

import com.fullstackev2.inventario.mapper.MovimientoStockMapper;
import com.fullstackev2.inventario.model.Inventario;
import com.fullstackev2.inventario.model.MovimientoStock;
import com.fullstackev2.inventario.repository.InventarioRepository;
import com.fullstackev2.inventario.repository.MovimientoStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MovimientoStockService {
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private MovimientoStockRepository movimientoStockRepository;

    public List<MovimientoStockDTO> obtenerMovimientos() {
        return movimientoStockRepository.findAll()
                .stream()
                .map(MovimientoStockMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public MovimientoStockDTO guardar(MovimientoStockDTO dto) {

        Inventario inventario = inventarioRepository.findById(dto.getInventarioId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        if (dto.getTipoMovimiento().equalsIgnoreCase("Entrada")) {
            inventario.setCantidadDisponible(inventario.getCantidadDisponible() + dto.getCantidad());
        }else if(dto.getTipoMovimiento().equalsIgnoreCase("Salida")){
            if(inventario.getCantidadDisponible() < dto.getCantidad()){
                throw new RuntimeException("Stock insuficiente");
            }
            inventario.setCantidadDisponible(inventario.getCantidadDisponible() - dto.getCantidad());
        }else{throw new RuntimeException("Tipo Movimiento debe ser Entrada o Salida");}

        inventarioRepository.save(inventario);
        MovimientoStock mov = MovimientoStockMapper.toEntity(dto);
        mov.setInventario(inventario);
        MovimientoStock guardado = movimientoStockRepository.save(mov);
        return MovimientoStockMapper.toDTO(guardado);
    }
    public Optional<MovimientoStockDTO> buscarPorId(Integer id) {
        return movimientoStockRepository.findById(id)
                .map(MovimientoStockMapper::toDTO);
    }
    public Optional<MovimientoStockDTO> actualizarPorId(Integer id, MovimientoStockDTO mov) {
        MovimientoStock movimiento = movimientoStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        Inventario inventario = inventarioRepository.findById(mov.getInventarioId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        movimiento.setTipoMovimiento(mov.getTipoMovimiento());
        movimiento.setMotivo(mov.getMotivo());
        movimiento.setCantidad(mov.getCantidad());
        movimiento.setAprobado(mov.getAprobado());
        movimiento.setFechaMovimiento(mov.getFechaMovimiento());
        movimiento.setInventario(inventario);
        MovimientoStock actualizado = movimientoStockRepository.save(movimiento);
        return Optional.of(MovimientoStockMapper.toDTO(actualizado));
    }
    public boolean eliminarPorId(Integer id) {
        if (movimientoStockRepository.existsById(id)) {
            movimientoStockRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
