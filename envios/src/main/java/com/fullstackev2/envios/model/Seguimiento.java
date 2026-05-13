package com.fullstackev2.envios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="SEGUIMIENTO")
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String estado;
    @Column(nullable = false)
    private String direccionEnvio;
    @Column(nullable = false)
    private LocalDate fechaDespacho;
    @Column(nullable = false)
    private Boolean activo;
    @Column(nullable = false)
    private Double ancho;
    @Column(nullable = false)
    private Double alto;
    @Column(nullable = false)
    private Boolean entregado;

    @ManyToOne
    @JoinColumn(name="envio_id", nullable = false)
    private Envio envio;

}
