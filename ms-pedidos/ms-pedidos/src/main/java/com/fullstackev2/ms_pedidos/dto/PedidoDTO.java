package com.fullstackev2.ms_pedidos.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Integer id;
    private String estado;
    private String direccionEntrega;
    private Double total;
    private boolean pagado;
    private LocalDate fechaPedido;
    private Integer usuarioId;
}