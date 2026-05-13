package com.fullstackev2.ms_pedidos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotBlank(message = "El estado es obligatorio")
    @Size(min = 2, max = 50, message = "Estado entre 2 y 50 caracteres")
    private String estado;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 200)
    private String direccionEntrega;

    @Positive(message = "El total debe ser positivo")
    private Double total;

    private boolean pagado;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaPedido;

    @NotNull(message = "El usuario es obligatorio")
    private Integer usuarioId;
}