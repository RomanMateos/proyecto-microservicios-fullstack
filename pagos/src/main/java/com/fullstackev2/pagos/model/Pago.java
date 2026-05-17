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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @Column(name = "detalle", nullable = false, length = 255)
    private String detalle;

    @Column(name = "monto_pago", nullable = false)
    private Double montoPago;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "aceptado", nullable = false)
    private Boolean aceptado;

    @Column(name = "pedido_id", nullable = false)
    private Integer pedidoId;
}