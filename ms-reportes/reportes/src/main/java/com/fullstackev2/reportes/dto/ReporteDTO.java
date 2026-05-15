package com.fullstackev2.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    private Integer reporteId;
    private String descripcion;
    private Double montoTotal;
    private LocalDate fechaGeneracion;
}