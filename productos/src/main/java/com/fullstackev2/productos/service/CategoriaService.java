package com.fullstackev2.productos.service;

import com.fullstackev2.productos.dto.CategoriaDTO;
import com.fullstackev2.productos.mapper.CategoriaMapper;
import com.fullstackev2.productos.model.Categoria;
import com.fullstackev2.productos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> obtenerCategorias(){
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaMapper::toDTO)
                .collect(Collectors.toList());
    }
    public CategoriaDTO guardar(CategoriaDTO dto){
        Categoria categoria = CategoriaMapper.toEntity(dto);
        Categoria guardado =  categoriaRepository.save(categoria);
        return CategoriaMapper.toDTO(guardado);
    }
    public Optional<CategoriaDTO> buscarPorId(Integer id){
        return categoriaRepository.findById(id)
                .map(CategoriaMapper::toDTO);
    }
    public Optional<CategoriaDTO> actualizarPorId(Integer id, CategoriaDTO dto){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setActivo(dto.getActivo());
        categoria.setCantidadProductos(dto.getCantidad());
        categoria.setFechaCreacion(dto.getFechaCreacion());

        Categoria actualizado =  categoriaRepository.save(categoria);
        return Optional.of(CategoriaMapper.toDTO(actualizado));

    }
    public boolean eliminarPorId(Integer id){
        if(categoriaRepository.existsById(id)){
            categoriaRepository.deleteById(id);
            return true;

        }
        return false;
    }
}
