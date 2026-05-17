package com.fullstackev2.sucursales.mapper;

import com.fullstackev2.sucursales.dto.SucursalRequestDTO;
import com.fullstackev2.sucursales.dto.SucursalResponseDTO;
import com.fullstackev2.sucursales.model.Region;
import com.fullstackev2.sucursales.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public SucursalResponseDTO toResponseDTO(Sucursal s) {
        if (s == null) return null;
        SucursalResponseDTO dto = new SucursalResponseDTO();
        dto.setSucursalId(s.getSucursalId());
        dto.setNombre(s.getNombre());
        dto.setDireccion(s.getDireccion());
        dto.setTelefono(s.getTelefono());
        dto.setCapacidad(s.getCapacidad());
        dto.setActivo(s.isActivo());
        dto.setFechaApertura(s.getFechaApertura());
        dto.setRegionId(s.getRegion().getRegionId());
        return dto;
    }

    public Sucursal toModel(SucursalRequestDTO dto, Region region) {
        if (dto == null) return null;
        Sucursal s = new Sucursal();
        s.setNombre(dto.getNombre());
        s.setDireccion(dto.getDireccion());
        s.setTelefono(dto.getTelefono());
        s.setCapacidad(dto.getCapacidad());
        s.setActivo(dto.getActivo());
        s.setFechaApertura(dto.getFechaApertura());
        s.setRegion(region);
        return s;
    }
}