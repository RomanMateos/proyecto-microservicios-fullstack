package com.fullstackev2.empleados.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoResponseDTO extends RepresentationModel<EmpleadoResponseDTO> {
    private Integer empleadoId;
    private String nombre;
    private String email;
    private String cargo;
    private Double salario;
    private Boolean activo;
    private LocalDate fechaIngreso;
    private Integer sucursalId;
}