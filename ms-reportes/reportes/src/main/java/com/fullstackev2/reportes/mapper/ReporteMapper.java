package com.fullstackev2.reportes.mapper;

import com.fullstackev2.reportes.dto.ReporteDTO;
import com.fullstackev2.reportes.model.Reporte;
import org.springframework.stereotype.Component;

@Component
public class ReporteMapper {

    public ReporteDTO toDTO(Reporte reporte) {
        if (reporte == null) return null;
        ReporteDTO dto = new ReporteDTO();
        dto.setReporteId(reporte.getReporteId());
        dto.setDescripcion(reporte.getDescripcion());
        dto.setMontoTotal(reporte.getMontoTotal());
        dto.setFechaGeneracion(reporte.getFechaGeneracion());
        return dto;
    }
}