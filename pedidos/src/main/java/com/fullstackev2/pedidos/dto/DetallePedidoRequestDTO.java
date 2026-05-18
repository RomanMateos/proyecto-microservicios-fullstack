package com.fullstackev2.pedidos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoRequestDTO {
    @NotNull(message = "El producto es obligatorio")
    private Integer productoId;

    @NotNull(message = "El pedido es obligatorio")
    private Integer pedidoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    private Boolean activo;
}