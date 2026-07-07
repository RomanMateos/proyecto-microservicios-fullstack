package com.fullstackev2.reportes.service;

import com.fullstackev2.reportes.client.EnvioClient;
import com.fullstackev2.reportes.client.PagoClient;
import com.fullstackev2.reportes.client.PedidoClient;
import com.fullstackev2.reportes.dto.*;
import com.fullstackev2.reportes.mapper.ReporteMapper;
import com.fullstackev2.reportes.model.Reporte;
import com.fullstackev2.reportes.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Mockito gestiona los mocks sin levantar Spring ni la BD
@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    // Simulamos el repositorio de reportes
    @Mock
    private ReporteRepository reporteRepository;

    // Simulamos el mapper
    @Mock
    private ReporteMapper reporteMapper;

    // Simulamos los tres FeignClients - así no necesitamos los otros microservicios corriendo
    @Mock
    private PedidoClient pedidoClient;

    @Mock
    private PagoClient pagoClient;

    @Mock
    private EnvioClient envioClient;

    // El Service real con los mocks inyectados
    @InjectMocks
    private ReporteService reporteService;

    // Datos de prueba reutilizados en todos los tests
    private Reporte reporte;
    private ReporteRequestDTO requestDTO;
    private ReporteResponseDTO responseDTO;

    // Se ejecuta antes de cada test para preparar datos limpios
    @BeforeEach
    void setUp() {
        // Reporte de ejemplo
        reporte = new Reporte();
        reporte.setReporteId(1);
        reporte.setTitulo("Reporte Mensual");
        reporte.setDescripcion("Reporte del mes de enero");
        reporte.setMontoTotal(500000.0);
        reporte.setTotalPedidos(10);
        reporte.setActivo(true);
        reporte.setFechaGeneracion(LocalDate.of(2024, 1, 31));

        // DTO de entrada
        requestDTO = new ReporteRequestDTO(
                "Reporte Mensual",
                "Reporte del mes de enero",
                500000.0,
                10,
                true,
                LocalDate.of(2024, 1, 31)
        );

        // DTO de salida
        responseDTO = new ReporteResponseDTO(
                1,
                "Reporte Mensual",
                "Reporte del mes de enero",
                500000.0,
                10,
                true,
                LocalDate.of(2024, 1, 31)
        );
    }

    // TEST 1: listarTodos debe retornar todos los reportes
    @Test
    void listarTodos_debeRetornarListaDeReportes() {
        // Given - el repositorio devuelve una lista con un reporte
        when(reporteRepository.findAll()).thenReturn(List.of(reporte));
        when(reporteMapper.toResponseDTO(reporte)).thenReturn(responseDTO);

        // When
        List<ReporteResponseDTO> resultado = reporteService.listarTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Reporte Mensual", resultado.get(0).getTitulo());
        verify(reporteRepository, times(1)).findAll();
    }

    // TEST 2: buscarPorId con ID existente debe retornar el reporte
    @Test
    void buscarPorId_cuandoExiste_debeRetornarDTO() {
        // Given
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));
        when(reporteMapper.toResponseDTO(reporte)).thenReturn(responseDTO);

        // When
        ReporteResponseDTO resultado = reporteService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getReporteId());
        assertEquals("Reporte Mensual", resultado.getTitulo());
        assertEquals(500000.0, resultado.getMontoTotal());
    }

    // TEST 3: buscarPorId con ID inexistente debe retornar null
    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarNull() {
        // Given - el repositorio no encuentra nada
        when(reporteRepository.findById(99)).thenReturn(Optional.empty());

        // When
        ReporteResponseDTO resultado = reporteService.buscarPorId(99);

        // Then
        assertNull(resultado);
    }

    // TEST 4: guardar reporte manual debe funcionar correctamente
    @Test
    void guardar_debeGuardarYRetornarDTO() {
        // Given
        when(reporteMapper.toModel(requestDTO)).thenReturn(reporte);
        when(reporteRepository.save(reporte)).thenReturn(reporte);
        when(reporteMapper.toResponseDTO(reporte)).thenReturn(responseDTO);

        // When
        ReporteResponseDTO resultado = reporteService.guardar(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Reporte Mensual", resultado.getTitulo());
        assertEquals(10, resultado.getTotalPedidos());
        verify(reporteRepository, times(1)).save(reporte);
    }

    // TEST 5: eliminar reporte existente debe retornar true
    @Test
    void eliminar_cuandoExiste_debeRetornarTrue() {
        // Given
        when(reporteRepository.existsById(1)).thenReturn(true);

        // When
        boolean resultado = reporteService.eliminar(1);

        // Then
        assertTrue(resultado);
        verify(reporteRepository, times(1)).deleteById(1);
    }

    // TEST 6: eliminar reporte inexistente debe retornar false
    @Test
    void eliminar_cuandoNoExiste_debeRetornarFalse() {
        // Given
        when(reporteRepository.existsById(99)).thenReturn(false);

        // When
        boolean resultado = reporteService.eliminar(99);

        // Then
        assertFalse(resultado);
        verify(reporteRepository, never()).deleteById(any());
    }

    // TEST 7: generarReporteConsolidado debe consumir los 3 FeignClients y guardar el reporte
    // Este es el test más importante porque prueba la comunicación entre microservicios
    @Test
    void generarReporteConsolidado_debeConsumirFeignClientsYGuardar() {
        // Given - simulamos las respuestas de los 3 microservicios via FeignClient
        PedidoDataDTO pedido = new PedidoDataDTO();
        pedido.setTotal(250000.0);

        PagoDataDTO pago = new PagoDataDTO();
        EnvioDataDTO envio = new EnvioDataDTO();

        // Configuramos qué devuelve cada FeignClient mockeado
        when(pedidoClient.obtenerPedidos()).thenReturn(List.of(pedido));
        when(pagoClient.obtenerPagos()).thenReturn(List.of(pago));
        when(envioClient.obtenerEnvios()).thenReturn(List.of(envio));

        // Simulamos que el repositorio guarda y devuelve el reporte
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporte);
        when(reporteMapper.toResponseDTO(reporte)).thenReturn(responseDTO);

        // When - ejecutamos el método que llama a los 3 microservicios
        ReporteResponseDTO resultado = reporteService.generarReporteConsolidado();

        // Then - verificamos que se llamó a cada FeignClient exactamente una vez
        verify(pedidoClient, times(1)).obtenerPedidos();
        verify(pagoClient, times(1)).obtenerPagos();
        verify(envioClient, times(1)).obtenerEnvios();

        // Verificamos que el reporte fue guardado en la BD
        verify(reporteRepository, times(1)).save(any(Reporte.class));

        // Verificamos que el resultado no es null
        assertNotNull(resultado);
    }
}