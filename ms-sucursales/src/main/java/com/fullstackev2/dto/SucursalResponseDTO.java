package com.fullstackev2.sucursales.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false) // Necesario al heredar de RepresentationModel
@NoArgsConstructor
@AllArgsConstructor
public class SucursalResponseDTO extends RepresentationModel<SucursalResponseDTO> {
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