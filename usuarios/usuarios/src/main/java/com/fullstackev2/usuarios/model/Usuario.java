package com.fullstackev2.usuarios.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name="USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombreCompleto;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private Integer edad;
    @Column(nullable = false)
    private LocalDate fechaNacimiento;
    @Column(nullable = false)
    private Boolean activo;
    @JsonManagedReference
    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    private List<Perfil> perfiles;
}
