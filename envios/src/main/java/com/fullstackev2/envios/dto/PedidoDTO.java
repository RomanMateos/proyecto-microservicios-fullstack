package com.fullstackev2.envios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Integer id;
    private String estado;
    private String direccionEntrega;
    private Double total;
    private Boolean pagado;
    private LocalDate fechaPedido;

    private Integer usuarioId;
}
