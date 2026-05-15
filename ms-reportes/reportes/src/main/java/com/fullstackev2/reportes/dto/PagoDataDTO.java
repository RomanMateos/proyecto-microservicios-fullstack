package com.fullstackev2.reportes.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDataDTO {
    private Integer pagoId;
    private Double monto;
    private String estadoPago;
}