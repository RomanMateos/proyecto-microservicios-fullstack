package com.fullstackev2.envios.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeguimientoDTO {
    private Integer id;

    @NotBlank(message = "El estado no puede estar en blanco")
    @Pattern(regexp = "ruta|entregado|sucursal",message = "Solo puede estar en (ruta, entregado o sucursal")
    private String estado;
    @NotBlank(message = "La direccion no puede estar en blanco")
    @NotNull(message = "La direccion no peude ser nula")
    private String direccionEnvio;
    @FutureOrPresent(message = "La fecha de despacho no puede ser anterior a hoy")
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate fechaDespacho;
    private Boolean activo;
    @DecimalMin(value="10.0",message = "El ancho no puede ser menor a 10cm")
    @DecimalMax(value="250.0",message = "El ancho no puede ser mayor a 250cm")
    private Double ancho;
    @DecimalMin(value="10.0",message = "El alto no puede ser menor a 10cm")
    @DecimalMax(value="250.0",message = "El alto no puede ser mayor a 250cm")
    private Double alto;
    private Boolean entregado;

    private Integer envioId;

}
