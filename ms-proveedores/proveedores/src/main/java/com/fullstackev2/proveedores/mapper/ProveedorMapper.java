package com.fullstackev2.proveedores.mapper;

import com.fullstackev2.proveedores.dto.ProveedorRequestDTO;
import com.fullstackev2.proveedores.dto.ProveedorResponseDTO;
import com.fullstackev2.proveedores.model.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    public ProveedorResponseDTO toResponseDTO(Proveedor p) {
        if (p == null) return null;
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setProveedorId(p.getProveedorId());
        dto.setNombre(p.getNombre());
        dto.setEmail(p.getEmail());
        dto.setTelefono(p.getTelefono());
        dto.setAniosExperiencia(p.getAniosExperiencia());
        dto.setActivo(p.isActivo());
        dto.setFechaRegistro(p.getFechaRegistro());
        return dto;
    }

    public Proveedor toModel(ProveedorRequestDTO dto) {
        if (dto == null) return null;
        Proveedor p = new Proveedor();
        p.setNombre(dto.getNombre());
        p.setEmail(dto.getEmail());
        p.setTelefono(dto.getTelefono());
        p.setAniosExperiencia(dto.getAniosExperiencia());
        p.setActivo(dto.getActivo());
        p.setFechaRegistro(dto.getFechaRegistro());
        return p;
    }
}