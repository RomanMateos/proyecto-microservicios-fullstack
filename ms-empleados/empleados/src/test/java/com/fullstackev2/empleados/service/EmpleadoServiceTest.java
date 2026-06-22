package com.fullstackev2.empleados.service;

import com.fullstackev2.empleados.dto.EmpleadoRequestDTO;
import com.fullstackev2.empleados.dto.EmpleadoResponseDTO;
import com.fullstackev2.empleados.mapper.EmpleadoMapper;
import com.fullstackev2.empleados.model.Empleado;
import com.fullstackev2.empleados.repository.EmpleadoRepository;
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

// Mockito crea los mocks sin levantar Spring ni conectarse a la BD
@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    // Simulamos el repositorio de empleados
    @Mock
    private EmpleadoRepository empleadoRepository;

    // Simulamos el mapper que convierte entre entidad y DTO
    @Mock
    private EmpleadoMapper empleadoMapper;

    // El Service real que vamos a probar con los mocks inyectados
    @InjectMocks
    private EmpleadoService empleadoService;

    // Datos de prueba reutilizados en todos los tests
    private Empleado empleado;
    private EmpleadoRequestDTO requestDTO;
    private EmpleadoResponseDTO responseDTO;

    // Se ejecuta antes de cada test para preparar datos limpios
    @BeforeEach
    void setUp() {
        // Empleado de ejemplo que usaremos en los tests
        empleado = new Empleado();
        empleado.setEmpleadoId(1);
        empleado.setNombre("Juan Pérez");
        empleado.setEmail("juan.perez@empresa.cl");
        empleado.setCargo("Analista");
        empleado.setSalario(850000.0);
        empleado.setActivo(true);
        empleado.setFechaIngreso(LocalDate.of(2022, 3, 1));
        empleado.setSucursalId(1);

        // DTO de entrada - lo que envía el cliente al crear un empleado
        requestDTO = new EmpleadoRequestDTO(
                "Juan Pérez",
                "juan.perez@empresa.cl",
                "Analista",
                850000.0,
                true,
                LocalDate.of(2022, 3, 1),
                1
        );

        // DTO de salida - lo que devuelve el sistema
        responseDTO = new EmpleadoResponseDTO(
                1,
                "Juan Pérez",
                "juan.perez@empresa.cl",
                "Analista",
                850000.0,
                true,
                LocalDate.of(2022, 3, 1),
                1
        );
    }

    // TEST 1: listarTodos debe retornar la lista completa de empleados
    @Test
    void listarTodos_debeRetornarListaDeEmpleados() {
        // Given - el repositorio devuelve una lista con un empleado
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));
        when(empleadoMapper.toResponseDTO(empleado)).thenReturn(responseDTO);

        // When - ejecutamos el método
        List<EmpleadoResponseDTO> resultado = empleadoService.listarTodos();

        // Then - verificamos que la lista tiene datos correctos
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
        verify(empleadoRepository, times(1)).findAll();
    }

    // TEST 2: buscarPorId con ID existente debe retornar el empleado
    @Test
    void buscarPorId_cuandoExiste_debeRetornarDTO() {
        // Given
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(empleadoMapper.toResponseDTO(empleado)).thenReturn(responseDTO);

        // When
        EmpleadoResponseDTO resultado = empleadoService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getEmpleadoId());
        assertEquals("juan.perez@empresa.cl", resultado.getEmail());
    }

    // TEST 3: buscarPorId con ID inexistente debe retornar null
    @Test
    void buscarPorId_cuandoNoExiste_debeRetornarNull() {
        // Given - el repositorio no encuentra nada con ese ID
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());

        // When
        EmpleadoResponseDTO resultado = empleadoService.buscarPorId(99);

        // Then - debe ser null porque no existe ese empleado
        assertNull(resultado);
    }

    // TEST 4: guardar empleado con email nuevo debe funcionar correctamente
    @Test
    void guardar_conEmailNuevo_debeGuardarYRetornarDTO() {
        // Given - el email NO existe en la BD
        when(empleadoRepository.existsByEmail("juan.perez@empresa.cl")).thenReturn(false);
        when(empleadoMapper.toModel(requestDTO)).thenReturn(empleado);
        when(empleadoRepository.save(empleado)).thenReturn(empleado);
        when(empleadoMapper.toResponseDTO(empleado)).thenReturn(responseDTO);

        // When
        EmpleadoResponseDTO resultado = empleadoService.guardar(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals(850000.0, resultado.getSalario());
        verify(empleadoRepository, times(1)).save(empleado);
    }

    // TEST 5: guardar empleado con email duplicado debe lanzar excepción
    @Test
    void guardar_conEmailDuplicado_debeLanzarExcepcion() {
        // Given - el email YA existe en la BD
        when(empleadoRepository.existsByEmail("juan.perez@empresa.cl")).thenReturn(true);

        // When & Then - debe lanzar IllegalArgumentException
        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> empleadoService.guardar(requestDTO)
        );

        // Verificamos que el mensaje menciona el email duplicado
        assertTrue(excepcion.getMessage().contains("El email ya está registrado"));
        // save nunca debe llamarse si el email ya existe
        verify(empleadoRepository, never()).save(any());
    }

    // TEST 6: eliminar empleado existente debe retornar true
    @Test
    void eliminar_cuandoExiste_debeRetornarTrue() {
        // Given - el empleado existe en la BD
        when(empleadoRepository.existsById(1)).thenReturn(true);

        // When
        boolean resultado = empleadoService.eliminar(1);

        // Then
        assertTrue(resultado);
        // verificamos que deleteById fue llamado exactamente una vez
        verify(empleadoRepository, times(1)).deleteById(1);
    }

    // TEST 7: eliminar empleado inexistente debe retornar false
    @Test
    void eliminar_cuandoNoExiste_debeRetornarFalse() {
        // Given - el empleado NO existe
        when(empleadoRepository.existsById(99)).thenReturn(false);

        // When
        boolean resultado = empleadoService.eliminar(99);

        // Then
        assertFalse(resultado);
        // deleteById nunca debe llamarse si el empleado no existe
        verify(empleadoRepository, never()).deleteById(any());
    }
}