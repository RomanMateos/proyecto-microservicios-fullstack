package com.fullstackev2.proveedores.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "contratos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contrato_id")
    private Integer contratoId;

    @Column(name = "numero_contrato", nullable = false, length = 50)
    private String numeroContrato;
    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;
    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;
    @Column(name = "activo", nullable = false)
    private boolean activo = true;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
        private Proveedor proveedor;

}