package com.fullstackev2.productos.service;

import com.fullstackev2.productos.dto.ProductoDTO;
import com.fullstackev2.productos.model.Categoria;
import com.fullstackev2.productos.model.Producto;
import com.fullstackev2.productos.repository.CategoriaRepository;
import com.fullstackev2.productos.repository.ProductoRepository;
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

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void obtenerProductos_deberiaRetornarListaDeProductos() {
        Categoria categoria = new Categoria();
        categoria.setId(1);

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombreProducto("Notebook Lenovo");
        producto.setDescripcion("Notebook Lenovo Gamer");
        producto.setPrecio(500000.0);
        producto.setFechaVencimiento(LocalDate.now().plusDays(30));
        producto.setDisponible(true);
        producto.setCategoria(categoria);

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoDTO> resultado = productoService.obtenerProductos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Notebook Lenovo", resultado.get(0).getNombreProducto());
        assertEquals(500000.0, resultado.get(0).getPrecio());
        assertEquals(1, resultado.get(0).getCategoriaId());

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarProducto() {
        Categoria categoria = new Categoria();
        categoria.setId(1);

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombreProducto("Mouse Gamer");
        producto.setDescripcion("Mouse gamer RGB");
        producto.setPrecio(25000.0);
        producto.setFechaVencimiento(LocalDate.now().plusDays(30));
        producto.setDisponible(true);
        producto.setCategoria(categoria);

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Optional<ProductoDTO> resultado = productoService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());
        assertEquals("Mouse Gamer", resultado.get().getNombreProducto());
        assertEquals(25000.0, resultado.get().getPrecio());
        assertEquals(1, resultado.get().getCategoriaId());

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
        dto.setDescripcion("Teclado mecánico RGB");
        dto.setPrecio(45000.0);
        dto.setFechaVencimiento(LocalDate.now().plusDays(30));
        dto.setDisponible(true);
        dto.setCategoriaId(1);

        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("Tecnología");

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1);
        productoGuardado.setNombreProducto("Teclado Mecánico");
        productoGuardado.setDescripcion("Teclado mecánico RGB");
        productoGuardado.setPrecio(45000.0);
        productoGuardado.setFechaVencimiento(LocalDate.now().plusDays(30));
        productoGuardado.setDisponible(true);
        productoGuardado.setCategoria(categoria);

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoGuardado);

        ProductoDTO resultado = productoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Teclado Mecánico", resultado.getNombreProducto());
        assertEquals(45000.0, resultado.getPrecio());
        assertEquals(1, resultado.getCategoriaId());

        verify(categoriaRepository, times(1)).findById(1);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void guardar_cuandoCategoriaNoExiste_deberiaLanzarExcepcion() {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto("Teclado Mecánico");
        dto.setDescripcion("Teclado mecánico RGB");
        dto.setPrecio(45000.0);
        dto.setFechaVencimiento(LocalDate.now().plusDays(30));
        dto.setDisponible(true);
        dto.setCategoriaId(99);

        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.guardar(dto);
        });

        assertEquals("Categoria no encontrada", exception.getMessage());

        verify(categoriaRepository, times(1)).findById(99);
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void actualizarPorId_cuandoExiste_deberiaActualizarProducto() {
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("Tecnología");

        Producto productoExistente = new Producto();
        productoExistente.setId(1);
        productoExistente.setNombreProducto("Producto Antiguo");
        productoExistente.setDescripcion("Descripcion antigua");
        productoExistente.setPrecio(10000.0);
        productoExistente.setFechaVencimiento(LocalDate.now().plusDays(20));
        productoExistente.setDisponible(true);
        productoExistente.setCategoria(categoria);

        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto("Producto Actualizado");
        dto.setDescripcion("Descripcion actualizada");
        dto.setPrecio(20000.0);
        dto.setFechaVencimiento(LocalDate.now().plusDays(30));
        dto.setDisponible(true);
        dto.setCategoriaId(1);

        Producto productoActualizado = new Producto();
        productoActualizado.setId(1);
        productoActualizado.setNombreProducto("Producto Actualizado");
        productoActualizado.setDescripcion("Descripcion actualizada");
        productoActualizado.setPrecio(20000.0);
        productoActualizado.setFechaVencimiento(LocalDate.now().plusDays(30));
        productoActualizado.setDisponible(true);
        productoActualizado.setCategoria(categoria);

        when(productoRepository.findById(1)).thenReturn(Optional.of(productoExistente));
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);

        Optional<ProductoDTO> resultado = productoService.actualizarPorId(1, dto);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());
        assertEquals("Producto Actualizado", resultado.get().getNombreProducto());
        assertEquals(20000.0, resultado.get().getPrecio());
        assertEquals(1, resultado.get().getCategoriaId());

        verify(productoRepository, times(1)).findById(1);
        verify(categoriaRepository, times(1)).findById(1);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void actualizarPorId_cuandoProductoNoExiste_deberiaLanzarExcepcion() {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombreProducto("Producto Actualizado");
        dto.setDescripcion("Descripcion actualizada");
        dto.setPrecio(20000.0);
        dto.setFechaVencimiento(LocalDate.now().plusDays(30));
        dto.setDisponible(true);
        dto.setCategoriaId(1);

        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.actualizarPorId(99, dto);
        });

        assertEquals("Producto no encontrado", exception.getMessage());

        verify(productoRepository, times(1)).findById(99);
        verify(categoriaRepository, never()).findById(anyInt());
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
        Categoria categoria = new Categoria();
        categoria.setId(1);

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombreProducto("Notebook Lenovo");
        producto.setDescripcion("Notebook Lenovo Gamer");
        producto.setPrecio(500000.0);
        producto.setFechaVencimiento(LocalDate.now().plusDays(30));
        producto.setDisponible(true);
        producto.setCategoria(categoria);

        when(productoRepository.findByNombreProductoContainingIgnoreCaseAndPrecioLessThan(
                "Notebook Lenovo", 500000.0))
                .thenReturn(List.of(producto));

        List<ProductoDTO> resultado = productoService.buscarPorNombreYPrecio(
                "Notebook Lenovo", 500000.0);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Notebook Lenovo", resultado.get(0).getNombreProducto());
        assertEquals(500000.0, resultado.get(0).getPrecio());
        assertEquals(1, resultado.get(0).getCategoriaId());

        verify(productoRepository, times(1))
                .findByNombreProductoContainingIgnoreCaseAndPrecioLessThan(
                        "Notebook Lenovo", 500000.0);
    }
}