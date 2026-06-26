package com.fullstackev2.ms_pedidos.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDTO {
    private Integer id;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private boolean activo;
    private LocalDate fechaAgregado;
    private Integer productoId;
    private Integer pedidoId;
}