package com.fullstackev2.proveedores.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratoRequestDTO {

    @NotBlank(message = "El número de contrato es obligatorio")
    @Size(min = 3, max = 50, message = "Debe tener entre 3 y 50 caracteres")
    private String numeroContrato;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 255, message = "Debe tener entre 5 y 255 caracteres")
    private String descripcion;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto no puede ser negativo")
    private Double montoTotal;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @PastOrPresent(message = "La fecha de inicio no puede ser futura")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser futura")
    private LocalDate fechaFin;

    @NotNull(message = "El ID del proveedor es obligatorio")
    @Positive(message = "El ID debe ser positivo")
    private Integer proveedorId;
}