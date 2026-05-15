package com.fullstackev2.proveedores.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proveedor_id")
    private Integer proveedorId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true, length = 150)

    private String email;

    @Column(name = "telefono", length = 20)
    private String telefono;


    @Column(name = "anios_experiencia", nullable = false)

    private Integer aniosExperiencia;


    @Column(name = "activo", nullable = false)
    private boolean activo = true;


    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;


    @OneToMany(
            mappedBy = "proveedor",

            cascade = CascadeType.ALL,

            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    private List<Contrato> contratos;
}