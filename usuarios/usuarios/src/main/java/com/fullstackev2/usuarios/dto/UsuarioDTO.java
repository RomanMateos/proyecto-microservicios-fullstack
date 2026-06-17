package com.fullstackev2.usuarios.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa la infromacion del usuario")
public class UsuarioDTO {
    @Schema(description = "Identificador unico del usuario",example = "1")
    private Integer id;
    @Schema(description = "Nombre completo del usuario", example = "Carlos Labbe Peña")
    @NotBlank(message="El nombre no puede estar vacio")
    @Size(min=3,max=60,message="Nombre invalido")
    private String nombreCompleto;
    @Schema(description = "Correo electronico del usuario", example = "c.labbe@duocuc.cl")
    @Email(message ="Correo invalido")
    @NotBlank(message="El mail no puede estar vacio")
    private String email;
    @Schema(description = "Direccion del domicilio del usuario", example = "avenida siempre viva 123")
    @Size(min=10,max=120,message="Direccion invalida")
    @NotBlank(message= "Debe ingresar una direccion")
    private String direccion;
    @Min(value=0)
    @Max(value=99)
    @Schema(description = "Edad del usuario", example = "31")
    private Integer edad;
    @NotNull(message="La fecha no puede estar en blanco")
    @Past(message="La fecha debe ser anterior a la de hoy")
    @Schema(description = "Fecha de nacimiento del usuario", example = "1995-04-05")
    private LocalDate fechaNacimiento;
    @Schema(description = "Estado del usuario",example = "true")
    private Boolean activo;

}
