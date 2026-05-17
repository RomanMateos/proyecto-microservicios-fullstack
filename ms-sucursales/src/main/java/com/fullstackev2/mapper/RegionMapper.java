package com.fullstackev2.sucursales.mapper;

import com.fullstackev2.sucursales.dto.RegionRequestDTO;
import com.fullstackev2.sucursales.dto.RegionResponseDTO;
import com.fullstackev2.sucursales.model.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    public RegionResponseDTO toResponseDTO(Region r) {
        if (r == null) return null;
        RegionResponseDTO dto = new RegionResponseDTO();
        dto.setRegionId(r.getRegionId());
        dto.setNombre(r.getNombre());
        dto.setCodigo(r.getCodigo());
        dto.setDescripcion(r.getDescripcion());
        dto.setNumeroComunas(r.getNumeroComunas());
        dto.setActivo(r.isActivo());
        dto.setFechaCreacion(r.getFechaCreacion());
        return dto;
    }

    public Region toModel(RegionRequestDTO dto) {
        if (dto == null) return null;
        Region r = new Region();
        r.setNombre(dto.getNombre());
        r.setCodigo(dto.getCodigo());
        r.setDescripcion(dto.getDescripcion());
        r.setNumeroComunas(dto.getNumeroComunas());
        r.setActivo(dto.getActivo());
        r.setFechaCreacion(dto.getFechaCreacion());
        return r;
    }
}