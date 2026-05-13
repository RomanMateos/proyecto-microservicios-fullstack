package com.fullstackev2.inventario.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Integer id;

    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private LocalDate fechaVencimiento;
    private Boolean disponible;

    private Integer categoriaId;
}