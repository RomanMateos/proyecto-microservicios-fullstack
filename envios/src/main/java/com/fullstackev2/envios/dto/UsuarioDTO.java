package com.fullstackev2.envios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;

    private String nombreCompleto;
    private String email;
    private String direccion;
    private Integer edad;
    private LocalDate fechaNacimiento;
    private Boolean activo;

}
