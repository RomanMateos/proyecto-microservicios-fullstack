package com.fullstackev2.empleados.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato válido")
    private String email;

    @NotBlank(message = "El cargo es obligatorio")
    @Size(min = 2, max = 100, message = "El cargo debe tener entre 2 y 100 caracteres")
    private String cargo;

    @NotNull(message = "El salario es obligatorio")
    @DecimalMin(value = "0.0", message = "El salario no puede ser negativo")
    private Double salario;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate fechaIngreso;

    @NotNull(message = "El ID de sucursal es obligatorio")
    @Positive(message = "El ID de sucursal debe ser positivo")
    private Integer sucursalId;
}