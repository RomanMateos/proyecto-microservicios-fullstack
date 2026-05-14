package com.fullstackev2.ms_pagos.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Integer id;
    private String metodoPago;
    private Double monto;
    private String estadoPago;
    private LocalDate fechaPago;
    private boolean pagado;
    private Integer pedidoId;
}