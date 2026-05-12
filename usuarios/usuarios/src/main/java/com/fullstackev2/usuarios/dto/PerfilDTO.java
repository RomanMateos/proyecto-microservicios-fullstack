package com.fullstackev2.usuarios.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    private Integer id;

    private String nombrePerfil;
    private String alias;
    private String email;
    private Integer edad;
    private LocalDate fechaNacimiento;
    private boolean activo;

    private Integer usuarioId;
}
