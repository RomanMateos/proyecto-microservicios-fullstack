package com.fullstackev2.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoStockDTO {
    private Integer id;

    @NotBlank(message="El tipo de movimiento no puede ser blanco" )
    @Pattern(regexp= "Entrada|Salida",message="Solo se acepta Entrada o Salida")
    private String tipoMovimiento;
    @NotBlank(message="El motivo no puede estar en blanco")
    @Size(min=6,max=60,message="Ingrese un motivo mas extenso")
    private String motivo;
    @Min(value=1,message="El valor debe ser mayor que 0")
    @Positive(message="El valor no puede negativo")
    @NotNull(message="Ingrese una cantidad admitible")
    private Integer cantidad;
    private Boolean aprobado;
    @FutureOrPresent(message="La fecha no puede ser anterior a hoy")
    @NotNull(message="No puede ser nulo")
    private LocalDate fechaMovimiento;

    private Integer inventarioId;
}
