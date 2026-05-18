package com.fullstackev2.reportes.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reporte_id")
    private Integer reporteId;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "total_pedidos", nullable = false)
    private Integer totalPedidos;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDate fechaGeneracion;
}