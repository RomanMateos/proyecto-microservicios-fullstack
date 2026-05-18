package com.fullstackev2.productos.mapper;

import com.fullstackev2.productos.dto.ProductoDTO;
import com.fullstackev2.productos.model.Producto;

public class ProductoMapper {
    public static ProductoDTO toDTO(Producto producto){
        return new ProductoDTO(
                producto.getId(),
                producto.getNombreProducto(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getFechaVencimiento(),
                producto.getDisponible(),
                producto.getCategoria() != null ? producto.getCategoria().getId() :null

        );
    }
    public static Producto toEntity(ProductoDTO dto){
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setFechaVencimiento(dto.getFechaVencimiento());
        producto.setDisponible(dto.getDisponible());

        return producto;
    }
}