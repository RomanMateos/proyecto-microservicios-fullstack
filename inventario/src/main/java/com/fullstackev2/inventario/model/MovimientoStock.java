package com.fullstackev2.inventario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="MOVIMIENTO_STOCK")
public class MovimientoStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    private String tipoMovimiento;
    @Column(nullable=false)
    private String motivo;
    @Column(nullable=false)
    private Integer cantidad;
    @Column(nullable=false)
    private Boolean aprobado;
    @Column(nullable=false)
    private LocalDate fechaMovimiento;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "inventario_id",nullable=false)
    private Inventario inventario;

}
