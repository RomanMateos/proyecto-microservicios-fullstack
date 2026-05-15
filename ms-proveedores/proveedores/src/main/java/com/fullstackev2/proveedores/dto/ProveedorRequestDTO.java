package com.fullstackev2.proveedores.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorRequestDTO {

    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato válido")
    private String email;

    @Size(max = 20, message = "El teléfono no puede superar 20 caracteres")
    private String telefono;

    @NotNull(message = "Los años de experiencia son obligatorios")
    @Min(value = 0, message = "No pueden ser negativos")
    @Max(value = 100, message = "No pueden superar 100")
    private Integer aniosExperiencia;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    @NotNull(message = "La fecha de registro es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaRegistro;
}