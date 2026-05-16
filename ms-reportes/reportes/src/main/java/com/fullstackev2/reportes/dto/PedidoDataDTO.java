package com.fullstackev2.reportes.dto;

import lombok.Data;

@Data
public class PedidoDataDTO {
    private Integer id;
    private Double total; // Asumiendo que ms-pedidos retorna un total
}