package com.fullstackev2.reportes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponseDTO extends RepresentationModel<ReporteResponseDTO> {

    private Integer reporteId;
    private String titulo;
    private String descripcion;
    private Double montoTotal;
    private Integer totalPedidos;
    private Boolean activo;
    private LocalDate fechaGeneracion;

}