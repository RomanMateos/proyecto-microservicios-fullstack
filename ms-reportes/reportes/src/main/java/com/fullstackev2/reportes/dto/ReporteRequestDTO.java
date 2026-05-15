package com.fullstackev2.reportes.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRequestDTO {

    @NotBlank(message = "El titulo es obligatorio")
    @Size(min = 2, max = 150, message = "El titulo debe tener entre 2 y 150 caracteres")
    private String titulo;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 5, max = 255, message = "La descripcion debe tener entre 5 y 255 caracteres")
    private String descripcion;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", message = "El monto no puede ser negativo")
    private Double montoTotal;

    @NotNull(message = "El total de pedidos es obligatorio")
    @Min(value = 0, message = "El total de pedidos no puede ser negativo")
    private Integer totalPedidos;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @NotNull(message = "La fecha de generacion es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaGeneracion;
}