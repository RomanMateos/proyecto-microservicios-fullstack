package com.fullstackev2.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioDTO {
    private Integer id;

    @NotBlank(message="La seccion no puede estar en blanco")
    @Size(min = 3, max=50,message="El nombre de la seccion no puede tener menos de 3 caracteres ni mas de 50")
    private String seccion;
    @Min(value=1)@Positive
    @NotNull(message="La cantidad disponible no peude ser nula")
    private Integer cantidadDisponible;
    @NotBlank(message="La ubicacion no puede estar en blanco")
    @Size(min=3,max=50,message="La ubicacion debe ser correcta")
    private String ubicacion;

    private Boolean activo;
    @NotNull(message="La fecha de actualizacion no puede ser nula")
    @FutureOrPresent(message="La fecha de actualizacion no puede ser anterior a hoy")
    private LocalDate fechaActualizacion;



}
