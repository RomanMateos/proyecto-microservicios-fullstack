package com.fullstackev2.pedidos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDTO {
    private Integer id;
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100)
    private String nombreProducto;
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    @Positive(message = "El precio debe ser positivo")
    private Double precioUnitario;
    private Boolean activo;
    @NotNull
    @PastOrPresent
    private LocalDate fechaAgregado;

    private Integer productoId;
    private Integer pedidoId;
}
