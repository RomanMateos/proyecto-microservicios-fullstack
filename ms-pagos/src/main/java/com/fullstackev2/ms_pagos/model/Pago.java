package com.fullstackev2.ms_pagos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PAGO")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String metodoPago;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private String estadoPago;

    @Column(nullable = false)
    private LocalDate fechaPago;

    @Column(nullable = false)
    private boolean pagado = false;

    @Column(nullable = false)
    private Integer pedidoId;
}