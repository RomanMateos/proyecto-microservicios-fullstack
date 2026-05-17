package com.fullstackev2.reportes.mapper;

import com.fullstackev2.reportes.dto.ReporteRequestDTO;
import com.fullstackev2.reportes.dto.ReporteResponseDTO;
import com.fullstackev2.reportes.model.Reporte;
import org.springframework.stereotype.Component;

@Component
public class ReporteMapper {

    public ReporteResponseDTO toResponseDTO(Reporte r) {
        if (r == null) return null;
        ReporteResponseDTO dto = new ReporteResponseDTO();
        dto.setReporteId(r.getReporteId());
        dto.setTitulo(r.getTitulo());
        dto.setDescripcion(r.getDescripcion());
        dto.setMontoTotal(r.getMontoTotal());
        dto.setTotalPedidos(r.getTotalPedidos());
        dto.setActivo(r.isActivo());
        dto.setFechaGeneracion(r.getFechaGeneracion());
        return dto;
    }

    public Reporte toModel(ReporteRequestDTO dto) {
        if (dto == null) return null;
        Reporte r = new Reporte();
        r.setTitulo(dto.getTitulo());
        r.setDescripcion(dto.getDescripcion());
        r.setMontoTotal(dto.getMontoTotal());
        r.setTotalPedidos(dto.getTotalPedidos());
        r.setActivo(dto.getActivo());
        r.setFechaGeneracion(dto.getFechaGeneracion());
        return r;
    }
}