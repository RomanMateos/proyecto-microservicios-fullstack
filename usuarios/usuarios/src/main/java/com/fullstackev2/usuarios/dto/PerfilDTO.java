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
@Schema(description = "DTO que representa la informacion de un  perfil")
public class PerfilDTO {
    @Schema(description = "Identificador unico del perfil",example = "1")
    private Integer id;
    @Schema(description = "Nombre del perfil",example = "Carlos Labbe")
    @NotBlank(message ="El nombre no puede estar vacio")
    @Size(min=5,max=20,message="El nombre no puede contener menos de 5 caracteres ni mas de 20")
    private String nombrePerfil;
    @Schema(description = "Alias asociado al perfil",example = "CLabbe.31")
    @NotBlank(message="El alias no puede estar vacio")
    @Size(min=5,max=30,message="El alias no puede tener menos de 5 caracteres ni mas de 30")
    private String alias;
    @Schema(description = "Corre electronico asociado al perfil",example = "c.labbe@duocuc.cl")
    @Email(message="El correo esta mal ingresado")
    @NotBlank(message="El campo mail no puede estar vacio")
    private String email;
    @Schema(description = "Edad del usuario del perfil",example = "31")
    @Min(value=1,message="Debes ingresar una edad valida")
    @Max(value=120,message="Debes ingresar uan edad valida")
    private Integer edad;
    @Schema(description = "Fecha de nacimiento del usuario del perfil", example = "1995/04/05")
    @Past(message="La fecha no puede ser hoy ni posterior")
    @NotNull(message="La fecha no puede estar en blanco")
    private LocalDate fechaNacimiento;
    @Schema(description = "Estado del perfil",example= "true")
    private Boolean activo;
    @Schema(description = "Identificador que lo asocia al usuario")
    private Integer usuarioId;
}
