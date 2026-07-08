package com.fullstackev2.sucursales.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(min = 5, max = 255, message = "La direccion debe tener entre 5 y 255 caracteres")
    private String direccion;

    @Size(max = 20, message = "El telefono no puede superar 20 caracteres")
    private String telefono;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad minima es 1")
    private Integer capacidad;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @NotNull(message = "La fecha de apertura es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaApertura;

    @NotNull(message = "El ID de region es obligatorio")
    @Positive(message = "El ID de region debe ser positivo")
    private Integer regionId;

    // PRUEBA 2: whitelist validation — mismo patrón que PagoDTO.metodoPago en pagos/
    @NotBlank(message = "La categoria es obligatoria")
    @Pattern(regexp = "MALL|CENTRO|STRIP|FLAGSHIP",
            message = "Categoria invalida. Valores permitidos: MALL, CENTRO, STRIP, FLAGSHIP")
    private String categoria;
}