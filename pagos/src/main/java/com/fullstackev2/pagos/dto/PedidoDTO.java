package com.fullstackev2.pagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Integer id;

    private String estado;
    private String direccionEntrega;
    private Double total;
    private Boolean pagado;
    private LocalDate fechaPedido;

    private Integer usuarioId;

}