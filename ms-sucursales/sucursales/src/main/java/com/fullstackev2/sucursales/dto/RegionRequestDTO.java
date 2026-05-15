package com.fullstackev2.sucursales.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El codigo es obligatorio")
    @Size(min = 1, max = 10, message = "El codigo debe tener entre 1 y 10 caracteres")
    private String codigo;

    @Size(max = 255, message = "La descripcion no puede superar 255 caracteres")
    private String descripcion;

    @NotNull(message = "El numero de comunas es obligatorio")
    @Min(value = 1, message = "Debe tener al menos 1 comuna")
    private Integer numeroComunas;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @NotNull(message = "La fecha de creacion es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaCreacion;
}