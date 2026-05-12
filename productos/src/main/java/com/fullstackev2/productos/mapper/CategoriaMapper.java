package com.fullstackev2.productos.mapper;

import com.fullstackev2.productos.dto.CategoriaDTO;
import com.fullstackev2.productos.model.Categoria;

public class CategoriaMapper {
    public static CategoriaDTO toDTO(Categoria categoria){
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getActivo(),
                categoria.getCantidadProductos(),
                categoria.getFechaCreacion()
        );
    }
    public static Categoria toEntity(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId());
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setActivo(categoriaDTO.getActivo());
        categoria.setCantidadProductos(categoriaDTO.getCantidad());
        categoria.setFechaCreacion(categoriaDTO.getFechaCreacion());
        return categoria;
    }
}
