package com.fullstackev2.productos.service;

import com.fullstackev2.productos.dto.ProductoDTO;
import com.fullstackev2.productos.mapper.ProductoMapper;
import com.fullstackev2.productos.model.Categoria;
import com.fullstackev2.productos.model.Producto;
import com.fullstackev2.productos.repository.CategoriaRepository;
import com.fullstackev2.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProductoDTO> obtenerProductos() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO guardar(ProductoDTO dto) {
        Producto producto = ProductoMapper.toEntity(dto);
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        producto.setCategoria(categoria);
        Producto guardado = productoRepository.save(producto);
        return ProductoMapper.toDTO(guardado);
    }

    public Optional<ProductoDTO> buscarPorId(Integer id) {
        return productoRepository.findById(id)
                .map(ProductoMapper::toDTO);
    }

    public Optional<ProductoDTO> actualizarPorId(Integer id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setFechaVencimiento(dto.getFechaVencimiento());
        producto.setDisponible(dto.getDisponible());
        producto.setCategoria(categoria);

        Producto actualizado = productoRepository.save(producto);
        return Optional.of(ProductoMapper.toDTO(actualizado));
    }

    public boolean eliminarPorId(Integer id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ProductoDTO> buscarPorNombreYPrecio(String nombreProducto, Double precio) {
        return productoRepository
                .findByNombreProductoContainingIgnoreCaseAndPrecioLessThan(nombreProducto, precio)
                .stream()
                .map(ProductoMapper::toDTO)
                .collect(Collectors.toList());

    }
}