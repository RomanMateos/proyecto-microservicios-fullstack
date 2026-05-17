package com.fullstackev2.reportes.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponseDTO {
    private Integer reporteId;
    private String titulo;
    private String descripcion;
    private Double montoTotal;
    private Integer totalPedidos;
    private boolean activo;
    private LocalDate fechaGeneracion;
}