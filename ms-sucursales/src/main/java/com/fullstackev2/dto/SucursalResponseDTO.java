package com.fullstackev2.sucursales.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalResponseDTO {
    private Integer sucursalId;
    private String nombre;
    private String direccion;
    private String telefono;
    private Integer capacidad;
    private boolean activo;
    private LocalDate fechaApertura;
    private Integer regionId;
    private String categoria;   // PRUEBA 3 — sin validación, es solo salida de datos
}