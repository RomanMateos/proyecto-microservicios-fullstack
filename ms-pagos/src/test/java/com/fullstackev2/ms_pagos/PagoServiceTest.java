package com.fullstackev2.pagos;

import com.fullstackev2.pagos.client.PedidoClient;
import com.fullstackev2.pagos.dto.PagoDTO;
import com.fullstackev2.pagos.model.Pago;
import com.fullstackev2.pagos.repository.PagoRepository;
import com.fullstackev2.pagos.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Pruebas unitarias del PagoService
// Mockito simula el repositorio y FeignClient sin levantar la app
@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    // Simula el repositorio — no accede a BD real
    @Mock
    private PagoRepository pagoRepository;

    // Simula el FeignClient — no llama a ms-pedidos real
    @Mock
    private PedidoClient pedidoClient;

    // Inyecta los mocks en el servicio que estamos testeando
    @InjectMocks
    private PagoService pagoService;

    private Pago pago;
    private PagoDTO pagoDTO;

    // Se ejecuta antes de cada test para preparar datos
    @BeforeEach
    void setUp() {
        pago = new Pago(1, "Tarjeta", "Pago laptop",
                50000.0, LocalDate.now(), true, 1);

        pagoDTO = new PagoDTO();
        pagoDTO.setId(1);
        pagoDTO.setMetodoPago("Tarjeta");
        pagoDTO.setDetalle("Pago laptop");
        pagoDTO.setMontoPago(50000.0);
        pagoDTO.setFechaPago(LocalDate.now());
        pagoDTO.setAceptado(true);
        pagoDTO.setPedidoId(1);
    }

    // TEST 1 — Listar todos los pagos
    // Given: hay 2 pagos en BD
    // When: se llama obtenerTotalPedidos
    // Then: retorna lista con 2 elementos
    @Test
    void testObtenerTodosLosPagos() {
        // Given
        Pago pago2 = new Pago(2, "Efectivo", "Pago monitor",
                30000.0, LocalDate.now(), false, 2);
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pago, pago2));

        // When
        List<PagoDTO> resultado = pagoService.obtenerTotalPedidos();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pagoRepository, times(1)).findAll();
    }

    // TEST 2 — Buscar pago existente por ID
    // Given: existe pago con id 1
    // When: se busca por id 1
    // Then: retorna el pago correcto
    @Test
    void testBuscarPorIdExistente() {
        // Given
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));

        // When
        Optional<PagoDTO> resultado = pagoService.buscarPorId(1);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Tarjeta", resultado.get().getMetodoPago());
        verify(pagoRepository, times(1)).findById(1);
    }

    // TEST 3 — Buscar pago inexistente
    // Given: no existe pago con id 99
    // When: se busca por id 99
    // Then: retorna Optional vacío
    @Test
    void testBuscarPorIdNoExistente() {
        // Given
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        // When
        Optional<PagoDTO> resultado = pagoService.buscarPorId(99);

        // Then
        assertFalse(resultado.isPresent());
    }

    // TEST 4 — Guardar pago usando total de FeignClient
    // Given: FeignClient retorna 50000.0 para pedido 1
    // When: se guarda el pago
    // Then: el monto guardado es 50000.0
    @Test
    void testGuardarPagoConFeignClient() {
        // Given
        when(pedidoClient.obtenerTotalPedido(1)).thenReturn(50000.0);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        // When
        PagoDTO resultado = pagoService.guardar(pagoDTO);

        // Then
        assertNotNull(resultado);
        assertEquals(50000.0, resultado.getMontoPago());
        verify(pedidoClient, times(1)).obtenerTotalPedido(1);
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    // TEST 5 — Guardar pago con pedido inexistente lanza excepción
    // Given: FeignClient retorna null para pedido 99
    // When: se intenta guardar
    // Then: lanza RuntimeException
    @Test
    void testGuardarPagoConPedidoInexistente() {
        // Given
        pagoDTO.setPedidoId(99);
        when(pedidoClient.obtenerTotalPedido(99)).thenReturn(null);

        // When / Then
        assertThrows(RuntimeException.class,
                () -> pagoService.guardar(pagoDTO));
    }

    // TEST 6 — Eliminar pago existente
    // Given: existe pago con id 1
    // When: se elimina
    // Then: retorna true y llama deleteById
    @Test
    void testEliminarExistente() {
        // Given
        when(pagoRepository.existsById(1)).thenReturn(true);
        doNothing().when(pagoRepository).deleteById(1);

        // When
        Boolean resultado = pagoService.eliminarPorId(1);

        // Then
        assertTrue(resultado);
        verify(pagoRepository, times(1)).deleteById(1);
    }

    // TEST 7 — Eliminar pago inexistente
    // Given: no existe pago con id 99
    // When: se intenta eliminar
    // Then: retorna false sin llamar deleteById
    @Test
    void testEliminarNoExistente() {
        // Given
        when(pagoRepository.existsById(99)).thenReturn(false);

        // When
        Boolean resultado = pagoService.eliminarPorId(99);

        // Then
        assertFalse(resultado);
        verify(pagoRepository, never()).deleteById(any());
    }

    // TEST 8 — Obtener total del pedido via FeignClient
    // Given: FeignClient retorna 75000.0 para pedido 2
    // When: se llama obtenerTotalPedido
    // Then: retorna 75000.0
    @Test
    void testObtenerTotalPedido() {
        // Given
        when(pedidoClient.obtenerTotalPedido(2)).thenReturn(75000.0);

        // When
        Double resultado = pagoService.obtenerTotalPedido(2);

        // Then
        assertEquals(75000.0, resultado);
        verify(pedidoClient, times(1)).obtenerTotalPedido(2);
    }
}