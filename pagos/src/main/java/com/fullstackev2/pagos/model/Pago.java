package com.fullstackev2.pagos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="PAGOS")
public class Pago {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String metodoPago;
    private String detalle;
    private Double montoPago;
    private LocalDate fechaPago;
    private Boolean aceptado;

    private Integer pedidoId;

}
