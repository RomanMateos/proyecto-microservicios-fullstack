package com.fullstackev2.productos.service;

import com.fullstackev2.productos.dto.ProductoDTO;
import com.fullstackev2.productos.model.Producto;
import com.fullstackev2.productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void obtenerProductos_deberiaRetornarListaDeProductos() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombreProducto("Notebook Lenovo");
        producto.setPrecio(500000.0);

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoDTO> resultado = productoService.obtenerProductos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Notebook Lenovo", resultado.get(0).getNombreProducto());
        assertEquals(500000.0, resultado.get(0).getPrecio());

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarProducto() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombreProducto("Mouse Gamer");
        producto.setPrecio(25000.0);

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Optional<ProductoDTO> resultado = productoService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());
        assertEquals("Mouse Gamer", resultado.get().getNombreProducto());
        assertEquals(25000.0, resultado.get().getPrecio());

        verify(productoRepository, times(1)).findById(1);
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaRetornarOptionalVacio() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        Optional<ProductoDTO> resultado = productoService.buscarPorId(99);

        assertTrue(resultado.isEmpty());

        verify(productoRepository, times(1)).findById(99);
    }

    @Test
    void guardar_deberiaGuardarProductoYRetornarDTO() {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto("Teclado Mecánico");
        dto.setPrecio(45000.0);

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1);
        productoGuardado.setNombreProducto("Teclado Mecánico");
        productoGuardado.setPrecio(45000.0);

        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        ProductoDTO resultado = productoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Teclado Mecánico", resultado.getNombreProducto());
        assertEquals(45000.0, resultado.getPrecio());

        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void actualizarPorId_cuandoExiste_deberiaActualizarProducto() {
        Producto productoExistente = new Producto();
        productoExistente.setId(1);
        productoExistente.setNombreProducto("Producto Antiguo");
        productoExistente.setPrecio(10000.0);

        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto("Producto Actualizado");
        dto.setPrecio(20000.0);

        Producto productoActualizado = new Producto();
        productoActualizado.setId(1);
        productoActualizado.setNombreProducto("Producto Actualizado");
        productoActualizado.setPrecio(20000.0);

        when(productoRepository.findById(1)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);

        Optional<ProductoDTO> resultado = productoService.actualizarPorId(1, dto);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());
        assertEquals("Producto Actualizado", resultado.get().getNombreProducto());
        assertEquals(20000.0, resultado.get().getPrecio());

        verify(productoRepository, times(1)).findById(1);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void actualizarPorId_cuandoNoExiste_deberiaLanzarExcepcion() {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto("Producto Actualizado");
        dto.setPrecio(20000.0);

        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productoService.actualizarPorId(99, dto);
        });

        verify(productoRepository, times(1)).findById(99);
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void eliminarPorId_cuandoExiste_deberiaEliminarYRetornarTrue() {
        when(productoRepository.existsById(1)).thenReturn(true);

        boolean resultado = productoService.eliminarPorId(1);

        assertTrue(resultado);

        verify(productoRepository, times(1)).existsById(1);
        verify(productoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarPorId_cuandoNoExiste_deberiaRetornarFalse() {
        when(productoRepository.existsById(99)).thenReturn(false);

        boolean resultado = productoService.eliminarPorId(99);

        assertFalse(resultado);

        verify(productoRepository, times(1)).existsById(99);
        verify(productoRepository, never()).deleteById(99);
    }

    @Test
    void buscarPorNombreYPrecio_deberiaRetornarProductosFiltrados() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombreProducto("Notebook Lenovo");
        producto.setPrecio(500000.0);

        when(productoRepository.findByNombreProductoContainingIgnoreCaseAndPrecioLessThan("Notebook Lenovo", 500000.0))
                .thenReturn(List.of(producto));

        List<ProductoDTO> resultado = productoService.buscarPorNombreYPrecio("Notebook Lenovo", 500000.0);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Notebook Lenovo", resultado.get(0).getNombreProducto());
        assertEquals(500000.0, resultado.get(0).getPrecio());

        verify(productoRepository, times(1))
                .findByNombreProductoContainingIgnoreCaseAndPrecioLessThan("Notebook Lenovo", 500000.0);
    }
}