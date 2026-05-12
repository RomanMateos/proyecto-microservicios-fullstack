package com.fullstackev2.usuarios.dto;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    private Integer id;
    @NotBlank(message ="El nombre no puede estar vacio")
    @Size(min=5,max=20,message="El nombre no puede contener menos de 5 caracteres ni mas de 20")
    private String nombrePerfil;
    @NotBlank(message="El alias no puede estar vacio")
    @Size(min=5,max=30,message="El alias no puede tener menos de 5 caracteres ni mas de 30")
    private String alias;
    @Email
    @NotBlank(message="El campo mail no puede estar vacio")
    private String email;
    @Min(value=1,message="Debes ingresar una edad valida")
    @Max(value=120,message="Debes ingresar uan edad valida")
    private Integer edad;
    @Past
    @NotNull
    private LocalDate fechaNacimiento;
    private boolean activo;

    private Integer usuarioId;
}
