package com.fullstackev2.ms_pedidos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PEDIDO")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String direccionEntrega;

    @Column(nullable = false)
    private Double total;

    private boolean pagado = false;

    @Column(nullable = false)
    private LocalDate fechaPedido;

    private Integer usuarioId;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<DetallePedido> detalles;
}