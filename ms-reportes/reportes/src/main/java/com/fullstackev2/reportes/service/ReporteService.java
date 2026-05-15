package com.fullstackev2.reportes.service;

import com.fullstackev2.reportes.client.PedidoClient;
import com.fullstackev2.reportes.dto.PedidoDataDTO;
import com.fullstackev2.reportes.dto.ReporteDTO;
import com.fullstackev2.reportes.mapper.ReporteMapper;
import com.fullstackev2.reportes.model.Reporte;
import com.fullstackev2.reportes.repository.ReporteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReporteService {

    @Autowired
    private ReporteRepository repository;

    @Autowired
    private PedidoClient pedidoClient;

    @Autowired
    private ReporteMapper mapper;

    public List<ReporteDTO> obtenerHistorial() {
        log.info("Consultando historial de reportes generados");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReporteDTO generarReporteVentas() {
        log.info("Iniciando integración con ms-pedidos para generar reporte");

        try {
            // Llamada remota vía Feign
            List<PedidoDataDTO> pedidos = pedidoClient.obtenerPedidosParaReporte();

            // Sumamos los totales de los pedidos obtenidos
            Double sumaTotal = pedidos.stream()
                    .mapToDouble(p -> p.getTotal() != null ? p.getTotal() : 0.0)
                    .sum();

            Reporte nuevoReporte = new Reporte();
            nuevoReporte.setDescripcion("Reporte consolidado de " + pedidos.size() + " pedidos");
            nuevoReporte.setMontoTotal(sumaTotal);
            nuevoReporte.setFechaGeneracion(LocalDate.now());

            log.info("Reporte generado exitosamente con un total de: {}", sumaTotal);
            return mapper.toDTO(repository.save(nuevoReporte));

        } catch (Exception e) {
            log.error("Error al conectar con ms-pedidos: {}", e.getMessage());
            throw new RuntimeException("No se pudo obtener la información de pedidos para el reporte");
        }
    }
}