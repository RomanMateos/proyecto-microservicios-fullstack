package com.fullstackev2.ms_pagos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    @NotBlank(message = "El método de pago es obligatorio")
    @Size(min = 2, max = 50, message = "Entre 2 y 50 caracteres")
    private String metodoPago;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotBlank(message = "El estado del pago es obligatorio")
    @Size(min = 2, max = 30, message = "Entre 2 y 30 caracteres")
    private String estadoPago;

    @NotNull(message = "La fecha de pago es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaPago;

    private boolean pagado = false;

    @NotNull(message = "El id del pedido es obligatorio")
    @Positive(message = "El id del pedido debe ser positivo")
    private Integer pedidoId;
}