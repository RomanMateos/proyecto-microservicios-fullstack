package com.fullstackev2.pedidos;

import com.fullstackev2.pedidos.dto.PedidoDTO;
import com.fullstackev2.pedidos.model.Pedido;
import com.fullstackev2.pedidos.repository.PedidoRepository;
import com.fullstackev2.pedidos.service.PedidoService;
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

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId(1);
        pedido.setEstado("pendiente");
        pedido.setDireccionEntrega("Av. Siempre Viva 123");
        pedido.setTotal(50000.0);
        pedido.setPagado(false);
        pedido.setFechaPedido(LocalDate.now());
        pedido.setUsuarioId(1);

        pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1);
        pedidoDTO.setEstado("pendiente");
        pedidoDTO.setDireccionEntrega("Av. Siempre Viva 123");
        pedidoDTO.setTotal(50000.0);
        pedidoDTO.setPagado(false);
        pedidoDTO.setFechaPedido(LocalDate.now());
        pedidoDTO.setUsuarioId(1);
    }

    // TEST 1 — Listar todos los pedidos
    // Given: hay 2 pedidos en BD
    // When: se llama listarTodos
    // Then: retorna lista con 2 elementos
    @Test
    void testListarTodos() {
        // Given
        Pedido pedido2 = new Pedido();
        pedido2.setId(2);
        pedido2.setEstado("pagado");
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido, pedido2));

        // When
        List<PedidoDTO> resultado = pedidoService.listarTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    // TEST 2 — Buscar pedido existente
    // Given: existe pedido con id 1
    // When: se busca por id 1
    // Then: retorna pedido correcto
    @Test
    void testObtenerPorIdExistente() {
        // Given
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        // When
        PedidoDTO resultado = pedidoService.obtenerPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals("pendiente", resultado.getEstado());
        verify(pedidoRepository, times(1)).findById(1);
    }

    // TEST 3 — Buscar pedido inexistente lanza excepción
    // Given: no existe pedido con id 99
    // When: se busca por id 99
    // Then: lanza excepción
    @Test
    void testObtenerPorIdNoExistente() {
        // Given
        when(pedidoRepository.findById(99)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class,
                () -> pedidoService.obtenerPorId(99));
    }

    // TEST 4 — Guardar nuevo pedido
    // Given: se crea un pedidoDTO válido
    // When: se llama guardar
    // Then: retorna el pedido guardado
    @Test
    void testGuardarPedido() {
        // Given
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // When
        PedidoDTO resultado = pedidoService.guardar(pedidoDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("pendiente", resultado.getEstado());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    // TEST 5 — Eliminar pedido existente
    // Given: existe pedido con id 1
    // When: se elimina
    // Then: retorna true
    @Test
    void testEliminarExistente() {
        // Given
        when(pedidoRepository.existsById(1)).thenReturn(true);
        doNothing().when(pedidoRepository).deleteById(1);

        // When
        boolean resultado = pedidoService.eliminarPorId(1);

        // Then
        assertTrue(resultado);
        verify(pedidoRepository, times(1)).deleteById(1);
    }

    // TEST 6 — Eliminar pedido inexistente
    // Given: no existe pedido con id 99
    // When: se intenta eliminar
    // Then: retorna false
    @Test
    void testEliminarNoExistente() {
        // Given
        when(pedidoRepository.existsById(99)).thenReturn(false);

        // When
        boolean resultado = pedidoService.eliminarPorId(99);

        // Then
        assertFalse(resultado);
        verify(pedidoRepository, never()).deleteById(any());
    }

    // TEST 7 — Verificar estado del pedido
    // Given: existe pedido con estado pendiente
    // When: se obtiene por id
    // Then: el estado es pendiente
    @Test
    void testVerificarEstadoPedido() {
        // Given
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        // When
        PedidoDTO resultado = pedidoService.obtenerPorId(1);

        // Then
        assertEquals("pendiente", resultado.getEstado());
        assertFalse(resultado.isPagado());
    }

    // TEST 8 — Verificar total del pedido
    // Given: pedido con total 50000
    // When: se guarda
    // Then: el total es 50000
    @Test
    void testVerificarTotalPedido() {
        // Given
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // When
        PedidoDTO resultado = pedidoService.guardar(pedidoDTO);

        // Then
        assertEquals(50000.0, resultado.getTotal());
    }
}
