package com.fullstackev2.productos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Integer id;

    @NotBlank(message="El nombre no puede estar vacio")
    @Size(min=3,max=30,message="El nombre no puede tener menos de 3 caracteres ni mas de 30")
    private String nombreProducto;
    @NotBlank(message="La descripcion no puede estar vacia")
    @Size(min=10,message=("La descripcion es muy corta"))
    private String descripcion;
    @DecimalMin(value = "100.0" ,message="El valor debe ser superior a 100 pesos")
    @Positive(message="El valor debe ser positivo")
    private Double precio;
    @Min(value=1,message="La cantidad debe ser mayor que 0")
    @NotNull(message ="No puede ser nulo")
    private Integer stock;
    @NotNull(message="La fecha no puede estar en blanco")
    @Future(message="La fecha de vencimiento no puede ser hoy ni anterior al dia de hoy")
    private LocalDate fechaVencimiento;
    private Boolean disponible;
    private Integer categoriaId;

}
/* private Integer id;

    private String nombre;
    private String descripcion;
    private double precio;
    private Integer stock;
    private LocalDate fechaVencimiento;
    private boolean disponible;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}*/