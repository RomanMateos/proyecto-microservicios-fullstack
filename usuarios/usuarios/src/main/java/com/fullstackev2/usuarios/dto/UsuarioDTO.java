package com.fullstackev2.usuarios.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;

    @NotBlank(message="El nombre no puede estar vacio")
    @Size(min=3,max=60,message="Nombre invalido")
    private String nombreCompleto;
    @Email(message ="Correo invalido")
    @NotBlank(message="El mail no puede estar vacio")
    private String email;
    @Size(min=10,max=120,message="Direccion invalida")
    @NotBlank(message= "Debe ingresar una direccion")
    private String direccion;
    @Min(value=0)
    @Max(value=99)
    private Integer edad;
    @NotNull
    @Past(message="La fecha debe ser anterior a la de hoy")
    private LocalDate fechaNacimiento;
    private boolean activo;

}
