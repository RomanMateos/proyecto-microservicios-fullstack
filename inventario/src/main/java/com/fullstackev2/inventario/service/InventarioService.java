package com.fullstackev2.inventario.service;

import com.fullstackev2.inventario.client.ProductoClient;
import com.fullstackev2.inventario.dto.InventarioDTO;
import com.fullstackev2.inventario.dto.ProductoDTO;
import com.fullstackev2.inventario.mapper.InventarioMapper;
import com.fullstackev2.inventario.model.Inventario;
import com.fullstackev2.inventario.repository.InventarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    ProductoClient productoClient;
    public List<InventarioDTO> listarInventario(){
        log.info("[Inventario service] listarInventario");
        return inventarioRepository.findAll()
                .stream()
                .map(InventarioMapper::toDTO)
                .collect(Collectors.toList());
    }
    public InventarioDTO guardar(InventarioDTO dto) {
        log.info("[Inventario service] guardarInventario");
        ProductoDTO producto = productoClient.obtenerProductoPorId(dto.getProductoId());

        if(producto == null){throw new RuntimeException("Producto no encontrado");}
        Inventario inventario = InventarioMapper.toEntity(dto);
        Inventario guardado = inventarioRepository.save(inventario);

        return InventarioMapper.toDTO(guardado);
    }
    public Optional<InventarioDTO> buscarPorId(Integer id){
        log.info("[Inventario service] buscarPorId");
        return inventarioRepository.findById(id)
                .map(InventarioMapper::toDTO);
    }
    public Optional<InventarioDTO> actualizarPorId(Integer id, InventarioDTO dto) {
        log.info("[Inventario service] actualizarPorId");
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        ProductoDTO producto = productoClient.obtenerProductoPorId(dto.getProductoId());
        if(producto == null){
            throw new RuntimeException("Producto no encontrado");
        }
        inventario.setProductoId(dto.getProductoId());
        inventario.setSeccion( dto.getSeccion());
        inventario.setCantidadDisponible(dto.getCantidadDisponible());
        inventario.setUbicacion(dto.getUbicacion());
        inventario.setActivo(dto.getActivo());
        inventario.setFechaActualizacion(LocalDate.now());

        Inventario actualizado = inventarioRepository.save(inventario);
        return Optional.of(InventarioMapper.toDTO(actualizado));
    }
    public Boolean eliminarPorId(Integer id) {
        log.info("[Inventario service] eliminarPorId");
        if (inventarioRepository.existsById(id)) {
            inventarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<InventarioDTO> listarInventarioActivos(Integer cantidad) {
        log.info("[Inventario service] listarInventarioActivos");
        return inventarioRepository.findByCantidadDisponibleGreaterThanAndActivoTrue(cantidad)
                .stream()
                .map(InventarioMapper::toDTO)
                .collect(Collectors.toList());
    }



}
