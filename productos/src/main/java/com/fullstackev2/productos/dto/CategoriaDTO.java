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
@Schema(description = "DTO que representa la informacion de una categoria")
public class CategoriaDTO {

    @Schema(description = "Identificador unico de la categoria", example = "1")
    private Integer id;

    @Schema(description = "Nombre de la categoria", example = "Tecnologia")
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, max = 60, message = "El nombre no puede contener menos de 3 caracteres ni mas de 60")
    private String nombre;

    @Schema(description = "Descripcion de la categoria", example = "Productos tecnologicos y accesorios")
    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(min = 10, message = "La descripcion es muy corta")
    private String descripcion;

    @Schema(description = "Estado de la categoria", example = "true")
    @NotNull(message = "El estado no puede estar vacio")
    private Boolean activo;

    @Schema(description = "Cantidad de productos asociados a la categoria", example = "5")
    @NotNull(message = "La cantidad no puede estar vacia")
    @Min(value = 1, message = "La cantidad no puede ser inferior a 1")
    @Positive(message = "No se aceptan negativos")
    private Integer cantidad;

    @Schema(description = "Fecha de creacion de la categoria", example = "2026-06-21")
    @NotNull(message = "La fecha no puede estar vacia")
    @PastOrPresent(message = "La fecha no puede ser posterior a hoy")
    private LocalDate fechaCreacion;
}