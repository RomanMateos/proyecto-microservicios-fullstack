package com.fullstackev2.empleados.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoResponseDTO {
    private Integer empleadoId;
    private String nombre;
    private String email;
    private String cargo;
    private Double salario;
    private boolean activo;
    private LocalDate fechaIngreso;
    private Integer sucursalId;
}