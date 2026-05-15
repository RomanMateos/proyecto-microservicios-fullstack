package com.fullstackev2.proveedores.mapper;

import com.fullstackev2.proveedores.dto.ContratoRequestDTO;
import com.fullstackev2.proveedores.dto.ContratoResponseDTO;
import com.fullstackev2.proveedores.model.Contrato;
import com.fullstackev2.proveedores.model.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ContratoMapper {

    public ContratoResponseDTO toResponseDTO(Contrato c) {
        if (c == null) return null;
        ContratoResponseDTO dto = new ContratoResponseDTO();
        dto.setContratoId(c.getContratoId());
        dto.setNumeroContrato(c.getNumeroContrato());
        dto.setDescripcion(c.getDescripcion());
        dto.setMontoTotal(c.getMontoTotal());
        dto.setActivo(c.isActivo());
        dto.setFechaInicio(c.getFechaInicio());
        dto.setFechaFin(c.getFechaFin());
        dto.setProveedorId(c.getProveedor().getProveedorId());
        return dto;
    }

    public Contrato toModel(ContratoRequestDTO dto, Proveedor proveedor) {
        if (dto == null) return null;
        Contrato c = new Contrato();
        c.setNumeroContrato(dto.getNumeroContrato());
        c.setDescripcion(dto.getDescripcion());
        c.setMontoTotal(dto.getMontoTotal());
        c.setActivo(dto.getActivo());
        c.setFechaInicio(dto.getFechaInicio());
        c.setFechaFin(dto.getFechaFin());
        c.setProveedor(proveedor);
        return c;
    }
}