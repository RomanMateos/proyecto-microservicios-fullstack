package com.fullstackev2.pagos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagoDTO {
    private Integer id;

    @NotBlank(message="No puede estar en blanco")
    @Pattern(regexp="Efectivo|Tarjeta|Transferencia",message="Elija una opcion correcta(efectivo,tarjeta o transferencia)")
    private String metodoPago;
    @NotBlank(message = "No es un detalle valido")
    @Size(min=20,max=150,message="Ingrese un mejor detalle")
    private String detalle;
    @DecimalMin(value="100.0")
    @NotNull(message="Ingrese un monto permitido")
    private Double montoPago;
    @PastOrPresent(message="No puede ser posterior a hoy")
    @NotNull(message="No puede estar en blanco")
    private LocalDate fechaPago;
    private Boolean aceptado;

    private Integer pedidoId;

}
