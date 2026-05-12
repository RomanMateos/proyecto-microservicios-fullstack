package com.fullstackev2.productos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    private Integer id;

    @NotBlank(message="El nombre no puede estar vacio")
    @Size(min=3,max=60,message="El nombre no puede contener menos de 3 caracteres ni mas de 60")
    private String nombre;
    @NotBlank(message="La descripcion no puede estar vacia")
    @Size(min=10,message=("La descripcion es muy corta"))
    private String descripcion;
    private Boolean activo;
    @Min(value=1,message="La cantidad no puede ser inferior a 1")
    @Positive(message="No se aceptan negativos")
    private Integer cantidad;
    @NotNull
    @PastOrPresent(message = "La fecha no puede ser posterior a hoy")
    private LocalDate fechaCreacion;



}
