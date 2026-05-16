package com.fullstackev2.empleados.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empleado_id")
    private Integer empleadoId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "cargo", nullable = false, length = 100)
    private String cargo;

    @Column(name = "salario", nullable = false)
    private Double salario;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(name = "sucursal_id", nullable = false)
    private Integer sucursalId;
}