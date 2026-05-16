package com.fullstackev2.proveedores.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponseDTO {
    private Integer proveedorId;
    private String nombre;
    private String email;
    private String telefono;
    private Integer aniosExperiencia;
    private boolean activo;
    private LocalDate fechaRegistro;
}