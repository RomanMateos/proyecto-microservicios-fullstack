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

    private String nombreCompleto;
    private String email;
    private String direccion;
    private Integer edad;
    private LocalDate fechaNacimiento;
    private boolean activo;
    @JsonManagedReference
    @OneToMany(mappedBy = "usuario")
    private List<Perfil> perfiles;
}
