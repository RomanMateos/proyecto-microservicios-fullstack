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

    private String nombrePerfil;
    private String alias;
    private String email;
    private Integer edad;
    private LocalDate fechaNacimiento;
    boolean activo;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
