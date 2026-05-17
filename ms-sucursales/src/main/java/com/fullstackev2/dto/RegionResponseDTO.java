package com.fullstackev2.sucursales.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponseDTO {
    private Integer regionId;
    private String nombre;
    private String codigo;
    private String descripcion;
    private Integer numeroComunas;
    private boolean activo;
    private LocalDate fechaCreacion;
}