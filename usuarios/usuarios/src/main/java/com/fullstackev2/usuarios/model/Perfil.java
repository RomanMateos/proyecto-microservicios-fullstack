package com.fullstackev2.usuarios.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="PERFIL")
public class Perfil {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombrePerfil;
    @Column(nullable = false)
    private String alias;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Integer edad;
    @Column(nullable = false)
    private LocalDate fechaNacimiento;
    @Column(nullable = false)
    private Boolean activo;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuario_id",nullable=false)
    private Usuario usuario;


}
