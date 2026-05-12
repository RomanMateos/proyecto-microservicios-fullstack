package com.fullstackev2.inventario.service;

import com.fullstackev2.inventario.dto.InventarioDTO;
import com.fullstackev2.inventario.mapper.InventarioMapper;
import com.fullstackev2.inventario.model.Inventario;
import com.fullstackev2.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository inventarioRepository;
    public List<InventarioDTO> listarInventario(){
        return inventarioRepository.findAll()
                .stream()
                .map(InventarioMapper::toDTO)
                .collect(Collectors.toList());
    }
    public InventarioDTO guardar(InventarioDTO dto){
        Inventario inventario = InventarioMapper.toEntity(dto);
        Inventario guardado = inventarioRepository.save(inventario);
        return InventarioMapper.toDTO(guardado);
    }

}
