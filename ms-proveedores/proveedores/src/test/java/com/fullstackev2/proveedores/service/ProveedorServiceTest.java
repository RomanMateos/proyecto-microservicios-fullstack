package com.fullstackev2.proveedores.service;

import com.fullstackev2.proveedores.dto.ProveedorRequestDTO;
import com.fullstackev2.proveedores.dto.ProveedorResponseDTO;
import com.fullstackev2.proveedores.mapper.ProveedorMapper;
import com.fullstackev2.proveedores.model.Proveedor;
import com.fullstackev2.proveedores.repository.ProveedorRepository;
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

// Le dice a JUnit que use Mockito para crear los mocks automáticamente
@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    // Mock del repositorio - simula la BD sin conectarse realmente
    @Mock
    private ProveedorRepository proveedorRepository;

    // Mock del mapper - simula la conversión entidad <-> DTO
    @Mock
    private ProveedorMapper proveedorMapper;

    // El Service real que vamos a probar, con los mocks inyectados
    @InjectMocks
    private ProveedorService proveedorService;

    // Datos de prueba que usaremos en todos los tests
    private Proveedor proveedor;
    private ProveedorRequestDTO requestDTO;
    private ProveedorResponseDTO responseDTO;

    // Este método se ejecuta antes de cada test para preparar los datos
    @BeforeEach
    void setUp() {
        // Creamos un proveedor de ejemplo para usar en los tests
        proveedor = new Proveedor();
        proveedor.setProveedorId(1);
        proveedor.setNombre("Tech Supply");
        proveedor.setEmail("contacto@techsupply.cl");
        proveedor.setTelefono("+56999888777");
        proveedor.setAniosExperiencia(5);
        proveedor.setActivo(true);
        proveedor.setFechaRegistro(LocalDate.of(2024, 1, 1));

        // DTO de entrada (lo que envía el cliente)
        requestDTO = new ProveedorRequestDTO(
                "Tech Supply",
                "contacto@techsupply.cl",
                "+56999888777",
                5,
                true,
                LocalDate.of(2024, 1, 1)
        );

        // DTO de salida (lo que devuelve el sistema)
        responseDTO = new ProveedorResponseDTO(
                1,
                "Tech Supply",
                "contacto@techsupply.cl",
                "+56999888777",
                5,
                true,
                LocalDate.of(2024, 1, 1)
        );
    }

    // TEST 1: listarTodos debe devolver una lista con proveedores
    @Test
    void listarTodos_debeRetornarListaDeProveedores() {
        // Given - preparamos qué devolverá el mock del repositorio
        when(proveedorRepository.findAll()).thenReturn(List.of(proveedor));
        // preparamos qué devolverá el mock del mapper
        when(proveedorMapper.toResponseDTO(proveedor)).thenReturn(responseDTO);

        // When - ejecutamos el método que queremos probar
        List<ProveedorResponseDTO> resultado = proveedorService.listarTodos();

        // Then - verificamos que el resultado es correcto
        assertNotNull(resultado);               // la lista no debe ser null
        assertEquals(1, resultado.size());      // debe tener 1 elemento
        assertEquals("Tech Supply", resultado.get(0).getNombre()); // nombre correcto
        verify(proveedorRepository, times(1)).findAll(); // el repo fue llamado 1 vez
    }

    // TEST 2: buscarPorId con ID existente debe devolver el proveedor
    @Test
    void buscarPorId_cuandoExiste_debeRetornarDTO() {
        // Given
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedor));
        when(proveedorMapper.toResponseDTO(proveedor)).thenReturn(responseDTO);

        // When
        ProveedorResponseDTO resultado = proveedorService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getProveedorId());
        assertEquals("contacto@techsupply.cl", resultado.getEmail());
    }

    // TEST 3: buscarPorId con ID inexistente debe devolver null
    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarNull() {
        // Given - el repositorio no encuentra nada
        when(proveedorRepository.findById(99)).thenReturn(Optional.empty());

        // When
        ProveedorResponseDTO resultado = proveedorService.buscarPorId(99);

        // Then - el resultado debe ser null porque no existe
        assertNull(resultado);
    }

    // TEST 4: guardar proveedor con email nuevo debe funcionar correctamente
    @Test
    void guardar_conEmailNuevo_debeGuardarYRetornarDTO() {
        // Given - el email NO existe en la BD
        when(proveedorRepository.existsByEmail("contacto@techsupply.cl")).thenReturn(false);
        when(proveedorMapper.toModel(requestDTO)).thenReturn(proveedor);
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);
        when(proveedorMapper.toResponseDTO(proveedor)).thenReturn(responseDTO);

        // When
        ProveedorResponseDTO resultado = proveedorService.guardar(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Tech Supply", resultado.getNombre());
        verify(proveedorRepository, times(1)).save(proveedor); // save fue llamado
    }

    // TEST 5: guardar proveedor con email duplicado debe lanzar excepción
    @Test
    void guardar_conEmailDuplicado_debeLanzarExcepcion() {
        // Given - el email YA existe en la BD
        when(proveedorRepository.existsByEmail("contacto@techsupply.cl")).thenReturn(true);

        // When & Then - verificamos que lanza IllegalArgumentException
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> proveedorService.guardar(requestDTO)
        );

        // Verificamos el mensaje de la excepción
        assertTrue(excepcion.getMessage().contains("El email ya está registrado"));
        // save nunca debe llamarse si el email ya existe
        verify(proveedorRepository, never()).save(any());
    }

    // TEST 6: eliminar proveedor existente debe devolver true
    @Test
    void eliminar_cuandoExiste_debeRetornarTrue() {
        // Given - el proveedor existe
        when(proveedorRepository.existsById(1)).thenReturn(true);

        // When
        boolean resultado = proveedorService.eliminar(1);

        // Then
        assertTrue(resultado); // debe devolver true
        verify(proveedorRepository, times(1)).deleteById(1); // deleteById fue llamado
    }

    // TEST 7: eliminar proveedor inexistente debe devolver false
    @Test
    void eliminar_cuandoNoExiste_debeRetornarFalse() {
        // Given - el proveedor NO existe
        when(proveedorRepository.existsById(99)).thenReturn(false);

        // When
        boolean resultado = proveedorService.eliminar(99);

        // Then
        assertFalse(resultado); // debe devolver false
        verify(proveedorRepository, never()).deleteById(any()); // deleteById nunca fue llamado
    }
}