package com.fullstackev2.reportes.service;

import com.fullstackev2.reportes.client.EnvioClient;
import com.fullstackev2.reportes.client.PagoClient;
import com.fullstackev2.reportes.client.PedidoClient;
import com.fullstackev2.reportes.dto.*;
import com.fullstackev2.reportes.mapper.ReporteMapper;
import com.fullstackev2.reportes.model.Reporte;
import com.fullstackev2.reportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final ReporteMapper reporteMapper;
    private final PedidoClient pedidoClient;
    private final PagoClient pagoClient;
    private final EnvioClient envioClient;

    public List<ReporteResponseDTO> listarTodos() {
        log.info("[ReporteService] Listando todos los reportes");
        return reporteRepository.findAll().stream()
                .map(reporteMapper::toResponseDTO).toList();
    }

    public List<ReporteResponseDTO> listarActivos() {
        log.info("[ReporteService] Listando reportes activos");
        return reporteRepository.findReportesActivos().stream()
                .map(reporteMapper::toResponseDTO).toList();
    }

    public ReporteResponseDTO buscarPorId(Integer id) {
        log.info("[ReporteService] Buscando reporte id={}", id);
        Optional<Reporte> optional = reporteRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[ReporteService] Reporte id={} no encontrado", id);
            return null;
        }
        return reporteMapper.toResponseDTO(optional.get());
    }

    public ReporteResponseDTO guardar(ReporteRequestDTO dto) {
        log.info("[ReporteService] Guardando reporte manual");
        Reporte guardado = reporteRepository.save(reporteMapper.toModel(dto));
        log.info("[ReporteService] Reporte guardado id={}", guardado.getReporteId());
        return reporteMapper.toResponseDTO(guardado);
    }

    public ReporteResponseDTO actualizar(Integer id, ReporteRequestDTO dto) {
        log.info("[ReporteService] Actualizando reporte id={}", id);
        Optional<Reporte> optional = reporteRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[ReporteService] Reporte id={} no existe", id);
            return null;
        }
        Reporte existente = optional.get();
        existente.setTitulo(dto.getTitulo());
        existente.setDescripcion(dto.getDescripcion());
        existente.setMontoTotal(dto.getMontoTotal());
        existente.setTotalPedidos(dto.getTotalPedidos());
        existente.setActivo(dto.getActivo());
        existente.setFechaGeneracion(dto.getFechaGeneracion());
        log.info("[ReporteService] Reporte id={} actualizado", id);
        return reporteMapper.toResponseDTO(reporteRepository.save(existente));
    }

    public boolean eliminar(Integer id) {
        log.info("[ReporteService] Eliminando reporte id={}", id);
        if (!reporteRepository.existsById(id)) {
            log.warn("[ReporteService] Reporte id={} no existe", id);
            return false;
        }
        reporteRepository.deleteById(id);
        log.info("[ReporteService] Reporte id={} eliminado", id);
        return true;
    }

    public ReporteResponseDTO generarReporteConsolidado() {
        log.info("[ReporteService] Generando reporte consolidado desde pedidos, pagos y envios");
        try {
            List<PedidoDataDTO> pedidos = pedidoClient.obtenerPedidos();
            log.info("[ReporteService] Pedidos obtenidos: {}", pedidos.size());

            List<PagoDataDTO> pagos = pagoClient.obtenerPagos();
            log.info("[ReporteService] Pagos obtenidos: {}", pagos.size());

            List<EnvioDataDTO> envios = envioClient.obtenerEnvios();
            log.info("[ReporteService] Envios obtenidos: {}", envios.size());

            Double montoTotal = pedidos.stream()
                    .mapToDouble(p -> p.getTotal() != null ? p.getTotal() : 0.0)
                    .sum();

            Reporte reporte = new Reporte();
            reporte.setTitulo("Reporte Consolidado " + LocalDate.now());
            reporte.setDescripcion("Consolidado de " + pedidos.size() + " pedidos, " +
                    pagos.size() + " pagos y " + envios.size() + " envios");
            reporte.setMontoTotal(montoTotal);
            reporte.setTotalPedidos(pedidos.size());
            reporte.setActivo(true);
            reporte.setFechaGeneracion(LocalDate.now());

            Reporte guardado = reporteRepository.save(reporte);
            log.info("[ReporteService] Reporte consolidado generado id={}", guardado.getReporteId());
            return reporteMapper.toResponseDTO(guardado);

        } catch (Exception e) {
            log.error("[ReporteService] Error al generar reporte consolidado: {}", e.getMessage());
            throw new RuntimeException("Error al generar reporte consolidado: " + e.getMessage());
        }
    }
}