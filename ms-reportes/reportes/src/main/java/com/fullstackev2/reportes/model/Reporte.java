package com.fullstackev2.reportes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "monto_total")
    private Double montoTotal;

    @Column(name = "fecha_generacion")
    private LocalDate fechaGeneracion;
}