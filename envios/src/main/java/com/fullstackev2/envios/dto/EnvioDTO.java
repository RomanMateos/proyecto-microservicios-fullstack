package com.fullstackev2.envios.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnvioDTO {
    private Integer id;
    @NotBlank(message="La direccion no peude estar en blanco")
    @Size(min=6,max=50,message="Ingrese una direccion completa")
    private String direccionEnvio;
    @NotBlank(message = "El numero de seguimiento no puede estar en blanco")
    @Size(min=6,max=15,message = "El numero de seguimiento debe contener al menos 6 numeros")
    private String nroSeguimiento;
    @DecimalMin(value="1000.0",message = "El costo de envia no puede ser inferior a 1000")
    @NotNull(message = "No puede ser nulo el envio")
    private Double costoEnvio;

    private Boolean activo;
    @PastOrPresent(message = "La fecha no puede ser posterior a hoy")
    @NotNull(message = "No puede estar en blanco")
    private LocalDate fechaEnvio;
    @NotNull(message = "El pedido no puede ser nulo")
    private Integer pedidoId;
    @NotNull(message = "El usuario no puede ser nulo")
    private Integer usuarioId;
}
