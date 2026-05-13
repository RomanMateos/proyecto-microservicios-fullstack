package com.fullstackev2.inventario.model;

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
@Table(name="INVENTARIO")
public class Inventario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false)
    private String seccion;
    @Column(nullable=false)
    private Integer cantidadDisponible;
    @Column(nullable=false)
    private String ubicacion;
    @Column(nullable=false)
    private Boolean activo;
    @Column(nullable=false)
    private LocalDate fechaActualizacion;
    @Column(nullable=false)
    private Integer productoId;

    @JsonManagedReference
    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL)
    private List<MovimientoStock> movimientoStock;

}

