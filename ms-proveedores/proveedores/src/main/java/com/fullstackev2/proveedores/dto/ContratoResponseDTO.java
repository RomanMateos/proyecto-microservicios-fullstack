package com.fullstackev2.proveedores.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratoResponseDTO {
    private Integer contratoId;
    private String numeroContrato;
    private String descripcion;
    private Double montoTotal;
    private boolean activo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer proveedorId;
}