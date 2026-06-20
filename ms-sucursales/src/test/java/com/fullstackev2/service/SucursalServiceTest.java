package com.fullstackev2.service;

import com.fullstackev2.sucursales.dto.SucursalRequestDTO;
import com.fullstackev2.sucursales.dto.SucursalResponseDTO;
import com.fullstackev2.sucursales.mapper.SucursalMapper;
import com.fullstackev2.sucursales.model.Region;
import com.fullstackev2.sucursales.model.Sucursal;
import com.fullstackev2.sucursales.repository.RegionRepository;
import com.fullstackev2.sucursales.repository.SucursalRepository;
import com.fullstackev2.sucursales.service.SucursalService;
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

// Mockito gestiona los mocks automáticamente sin levantar Spring
@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

    // Simulamos el repositorio de sucursales sin BD real
    @Mock
    private SucursalRepository sucursalRepository;

    // Simulamos el repositorio de regiones sin BD real
    @Mock
    private RegionRepository regionRepository;

    // Simulamos el mapper sin lógica real de conversión
    @Mock
    private SucursalMapper sucursalMapper;

    // El Service real que vamos a probar con los mocks inyectados
    @InjectMocks
    private SucursalService sucursalService;

    // Objetos de prueba reutilizados en todos los tests
    private Sucursal sucursal;
    private Region region;
    private SucursalRequestDTO requestDTO;
    private SucursalResponseDTO responseDTO;

    // Se ejecuta antes de cada test para preparar los datos
    @BeforeEach
    void setUp() {
        // Región de ejemplo
        region = new Region();
        region.setRegionId(1);
        region.setNombre("Región Metropolitana");
        region.setCodigo("RM");
        region.setNumeroComunas(52);
        region.setActivo(true);
        region.setFechaCreacion(LocalDate.of(2020, 1, 1));

        // Sucursal de ejemplo
        sucursal = new Sucursal();
        sucursal.setSucursalId(1);
        sucursal.setNombre("Sucursal Santiago Centro");
        sucursal.setDireccion("Av. Libertador 1234");
        sucursal.setTelefono("+56222345678");
        sucursal.setCapacidad(50);
        sucursal.setActivo(true);
        sucursal.setFechaApertura(LocalDate.of(2021, 3, 15));
        sucursal.setRegion(region);

        // DTO de entrada
        requestDTO = new SucursalRequestDTO(
                "Sucursal Santiago Centro",
                "Av. Libertador 1234",
                "+56222345678",
                50,
                true,
                LocalDate.of(2021, 3, 15),
                1
        );

        // DTO de salida
        responseDTO = new SucursalResponseDTO(
                1,
                "Sucursal Santiago Centro",
                "Av. Libertador 1234",
                "+56222345678",
                50,
                true,
                LocalDate.of(2021, 3, 15),
                1
        );
    }

    // TEST 1: listarTodos debe retornar la lista de sucursales
    @Test
    void listarTodos_debeRetornarListaDeSucursales() {
        // Given
        when(sucursalRepository.findAll()).thenReturn(List.of(sucursal));
        when(sucursalMapper.toResponseDTO(sucursal)).thenReturn(responseDTO);

        // When
        List<SucursalResponseDTO> resultado = sucursalService.listarTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sucursal Santiago Centro", resultado.get(0).getNombre());
        verify(sucursalRepository, times(1)).findAll();
    }

    // TEST 2: buscarPorId con ID existente debe retornar el DTO
    @Test
    void buscarPorId_cuandoExiste_debeRetornarDTO() {
        // Given
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(sucursal));
        when(sucursalMapper.toResponseDTO(sucursal)).thenReturn(responseDTO);

        // When
        SucursalResponseDTO resultado = sucursalService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getSucursalId());
        assertEquals("Sucursal Santiago Centro", resultado.getNombre());
    }

    // TEST 3: buscarPorId con ID inexistente debe retornar null
    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarNull() {
        // Given - el repositorio no encuentra nada
        when(sucursalRepository.findById(99)).thenReturn(Optional.empty());

        // When
        SucursalResponseDTO resultado = sucursalService.buscarPorId(99);

        // Then - debe ser null porque no existe
        assertNull(resultado);
    }

    // TEST 4: guardar con región válida debe funcionar correctamente
    @Test
    void guardar_conRegionValida_debeGuardarYRetornarDTO() {
        // Given - la región existe
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(sucursalMapper.toModel(requestDTO, region)).thenReturn(sucursal);
        when(sucursalRepository.save(sucursal)).thenReturn(sucursal);
        when(sucursalMapper.toResponseDTO(sucursal)).thenReturn(responseDTO);

        // When
        SucursalResponseDTO resultado = sucursalService.guardar(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Sucursal Santiago Centro", resultado.getNombre());
        verify(sucursalRepository, times(1)).save(sucursal);
    }

    // TEST 5: guardar con región inexistente debe lanzar excepción
    @Test
    void guardar_conRegionInexistente_debeLanzarExcepcion() {
        // Given - la región NO existe
        when(regionRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then - debe lanzar IllegalArgumentException
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> sucursalService.guardar(requestDTO)
        );

        assertTrue(excepcion.getMessage().contains("Region no encontrada"));
        // save nunca debe llamarse si la región no existe
        verify(sucursalRepository, never()).save(any());
    }

    // TEST 6: eliminar sucursal existente debe retornar true
    @Test
    void eliminar_cuandoExiste_debeRetornarTrue() {
        // Given
        when(sucursalRepository.existsById(1)).thenReturn(true);

        // When
        boolean resultado = sucursalService.eliminar(1);

        // Then
        assertTrue(resultado);
        verify(sucursalRepository, times(1)).deleteById(1);
    }

    // TEST 7: eliminar sucursal inexistente debe retornar false
    @Test
    void eliminar_cuandoNoExiste_debeRetornarFalse() {
        // Given
        when(sucursalRepository.existsById(99)).thenReturn(false);

        // When
        boolean resultado = sucursalService.eliminar(99);

        // Then
        assertFalse(resultado);
        verify(sucursalRepository, never()).deleteById(any());
    }
}