package com.fullstackev2.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa la informacion de un producto")
public class ProductoDTO {

    @Schema(description = "Identificador unico del producto", example = "1")
    private Integer id;

    @Schema(description = "Nombre del producto", example = "Notebook Lenovo")
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, max = 30, message = "El nombre no puede tener menos de 3 caracteres ni mas de 30")
    private String nombreProducto;

    @Schema(description = "Descripcion del producto", example = "Notebook Lenovo Gamer")
    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(min = 10, message = "La descripcion es muy corta")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "500000")
    @NotNull(message = "El precio no puede estar vacio")
    @DecimalMin(value = "100.0", message = "El valor debe ser superior a 100 pesos")
    @Positive(message = "El valor debe ser positivo")
    private Double precio;

    @Schema(description = "Fecha de vencimiento del producto", example = "2026-12-31")
    @NotNull(message = "La fecha no puede estar en blanco")
    @Future(message = "La fecha de vencimiento no puede ser hoy ni anterior al dia de hoy")
    private LocalDate fechaVencimiento;

    @Schema(description = "Estado de disponibilidad del producto", example = "true")
    @NotNull(message = "La disponibilidad no puede estar vacia")
    private Boolean disponible;

    @Schema(description = "Identificador de la categoria asociada", example = "1")
    @NotNull(message = "La categoria no puede estar vacia")
    private Integer categoriaId;
}