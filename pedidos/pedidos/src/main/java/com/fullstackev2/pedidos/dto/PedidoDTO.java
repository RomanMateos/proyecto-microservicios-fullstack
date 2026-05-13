package com.fullstackev2.pedidos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Integer id;
    @NotBlank(message = "El estado es obligatorio")
    @Size(min = 2, max = 50, message = "Estado entre 2 y 50 caracteres")
    private String estado;
    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200)
    private String direccionEntrega;
    @Positive(message = "El total debe ser positivo")
    @DecimalMin(value="100.0")
    private Double total;
    private Boolean pagado;
    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaPedido;

    private Integer usuarioId;
}
