package com.fullstackev2.pedidos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {private Integer id;

    private String nombreCompleto;
    private String email;
    private String direccion;
    private Integer edad;
    private LocalDate fechaNacimiento;
    private Boolean activo;

}
