package com.fullstackev2.pagos.service;

import com.fullstackev2.pagos.client.PedidoClient;
import com.fullstackev2.pagos.dto.PagoDTO;
import com.fullstackev2.pagos.model.Pago;
import com.fullstackev2.pagos.repository.PagoRepository;
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

// PagoMapper es una clase estatica (no @Component), asi que NO se mockea:
// dejamos que el mapeo real ocurra, solo mockeamos repository y feign client
@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;
    private PagoDTO pagoDTO;

    @BeforeEach
    void setUp() {
        pago = new Pago(
                1,
                "Tarjeta",
                "Pago correspondiente a compra de notebook para oficina",
                250000.0,
                LocalDate.of(2026, 6, 1),
                true,
                10
        );

        pagoDTO = new PagoDTO(
                1,
                "Tarjeta",
                "Pago correspondiente a compra de notebook para oficina",
                250000.0,
                LocalDate.of(2026, 6, 1),
                true,
                10
        );
    }

    // TEST 1: obtenerTotalPedidos debe retornar la lista completa de pagos
    @Test
    void obtenerTotalPedidos_debeRetornarListaDePagos() {
        when(pagoRepository.findAll()).thenReturn(List.of(pago));

        List<PagoDTO> resultado = pagoService.obtenerTotalPedidos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tarjeta", resultado.get(0).getMetodoPago());
        verify(pagoRepository, times(1)).findAll();
    }

    // TEST 2: buscarPorId con ID existente debe retornar el DTO dentro de un Optional
    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptionalConDTO() {
        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));

        Optional<PagoDTO> resultado = pagoService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Tarjeta", resultado.get().getMetodoPago());
    }

    // TEST 3: buscarPorId con ID inexistente debe retornar Optional vacio
    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarOptionalVacio() {
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        Optional<PagoDTO> resultado = pagoService.buscarPorId(99);

        assertTrue(resultado.isEmpty());
    }

    // TEST 4: guardar con pedido valido (FeignClient responde con un total) debe guardar y retornar DTO
    @Test
    void guardar_conPedidoValido_debeGuardarYRetornarDTO() {
        when(pedidoClient.obtenerTotalPedido(10)).thenReturn(250000.0);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        PagoDTO resultado = pagoService.guardar(pagoDTO);

        assertNotNull(resultado);
        assertEquals(250000.0, resultado.getMontoPago());
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    // TEST 5: guardar con pedido inexistente (FeignClient devuelve null) debe lanzar excepcion
    @Test
    void guardar_conPedidoInexistente_debeLanzarExcepcion() {
        when(pedidoClient.obtenerTotalPedido(10)).thenReturn(null);

        RuntimeException excepcion = assertThrows(
                RuntimeException.class,
                () -> pagoService.guardar(pagoDTO)
        );
        assertEquals("Pedido no encontrado", excepcion.getMessage());
        verify(pagoRepository, never()).save(any());
    }

    // TEST 6: eliminarPorId con ID existente debe retornar true
    @Test
    void eliminarPorId_cuandoExiste_debeRetornarTrue() {
        when(pagoRepository.existsById(1)).thenReturn(true);

        Boolean resultado = pagoService.eliminarPorId(1);

        assertTrue(resultado);
        verify(pagoRepository, times(1)).deleteById(1);
    }

    // TEST 7: eliminarPorId con ID inexistente debe retornar false
    @Test
    void eliminarPorId_cuandoNoExiste_debeRetornarFalse() {
        when(pagoRepository.existsById(99)).thenReturn(false);

        Boolean resultado = pagoService.eliminarPorId(99);

        assertFalse(resultado);
        verify(pagoRepository, never()).deleteById(any());
    }
}
