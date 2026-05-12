package com.fullstackev2.productos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "CATEGORIA")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Boolean activo;
    @Column(nullable = false)
    private Integer cantidadProductos;
    @Column(nullable = false)
    private LocalDate fechaCreacion;

    @JsonManagedReference
    @OneToMany (mappedBy="categoria",cascade = CascadeType.ALL)
    private List<Producto> productos;

}
