package com.fullstackev2.reportes.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDataDTO {
    private Integer envioId;
    private String estado;
    private Boolean entregado;
}