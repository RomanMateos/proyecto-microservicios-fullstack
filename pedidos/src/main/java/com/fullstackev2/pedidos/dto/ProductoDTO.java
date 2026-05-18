package com.fullstackev2.pedidos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductoDTO {
    private Integer id;

    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private LocalDate fechaVencimiento;
    private Boolean disponible;
    private Integer categoriaId;
}
