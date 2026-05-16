package com.fullstackev2.productos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="PRODUCTO")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombreProducto;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Double precio;
    private LocalDate fechaVencimiento;
    @Column(nullable = false)
    private Boolean disponible;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "categoria_id",nullable = false)
    private Categoria categoria;
}
