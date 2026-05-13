package com.fullstackev2.envios.model;

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
@Table(name="ENVIOS")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String direccionEnvio;
    @Column(nullable = false)
    private String nroSeguimiento;
    @Column(nullable = false)
    private Double costoEnvio;
    @Column(nullable = false)
    private Boolean activo;
    @Column(nullable = false)
    private LocalDate fechaEnvio;
    @Column(nullable = false)
    private Integer usuarioId;
    @Column(nullable = false)
    private Integer pedidoId;


    @OneToMany(mappedBy = "envio", cascade = CascadeType.ALL)
    private List<Seguimiento> seguimientos;




}
