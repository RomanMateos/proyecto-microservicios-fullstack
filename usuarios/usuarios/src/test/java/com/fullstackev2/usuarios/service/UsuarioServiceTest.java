package com.fullstackev2.usuarios.service;

import com.fullstackev2.usuarios.dto.UsuarioDTO;
import com.fullstackev2.usuarios.model.Usuario;
import com.fullstackev2.usuarios.repository.UsuarioRepository;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void obtenerUsuarios_deberiaRetornarListaDeUsuarios() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreCompleto("Carlos Labbe");
        usuario.setEmail("carlos@test.cl");
        usuario.setDireccion("Avenida Siempre Viva 123");
        usuario.setEdad(31);
        usuario.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        usuario.setActivo(true);

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioDTO> resultado = usuarioService.obtenerUsuarios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos Labbe", resultado.get(0).getNombreCompleto());
        assertEquals("carlos@test.cl", resultado.get(0).getEmail());

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void guardar_deberiaGuardarUsuarioYRetornarDTO() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombreCompleto("Carlos Labbe");
        dto.setEmail("carlos@test.cl");
        dto.setDireccion("Avenida Siempre Viva 123");
        dto.setEdad(31);
        dto.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        dto.setActivo(true);

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1);
        usuarioGuardado.setNombreCompleto("Carlos Labbe");
        usuarioGuardado.setEmail("carlos@test.cl");
        usuarioGuardado.setDireccion("Avenida Siempre Viva 123");
        usuarioGuardado.setEdad(31);
        usuarioGuardado.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        usuarioGuardado.setActivo(true);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        UsuarioDTO resultado = usuarioService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Carlos Labbe", resultado.getNombreCompleto());
        assertEquals("carlos@test.cl", resultado.getEmail());

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreCompleto("Carlos Labbe");
        usuario.setEmail("carlos@test.cl");
        usuario.setDireccion("Avenida Siempre Viva 123");
        usuario.setEdad(31);
        usuario.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        usuario.setActivo(true);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<UsuarioDTO> resultado = usuarioService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());
        assertEquals("Carlos Labbe", resultado.get().getNombreCompleto());

        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaRetornarOptionalVacio() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        Optional<UsuarioDTO> resultado = usuarioService.buscarPorId(99);

        assertTrue(resultado.isEmpty());

        verify(usuarioRepository, times(1)).findById(99);
    }

    @Test
    void eliminarPorId_cuandoExiste_deberiaEliminarYRetornarTrue() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        boolean resultado = usuarioService.eliminarPorId(1);

        assertTrue(resultado);

        verify(usuarioRepository, times(1)).existsById(1);
        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarPorId_cuandoNoExiste_deberiaRetornarFalse() {
        when(usuarioRepository.existsById(99)).thenReturn(false);

        boolean resultado = usuarioService.eliminarPorId(99);

        assertFalse(resultado);

        verify(usuarioRepository, times(1)).existsById(99);
        verify(usuarioRepository, never()).deleteById(99);
    }

    @Test
    void buscarPorEmailYActivo_deberiaRetornarUsuariosFiltrados() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreCompleto("Carlos Labbe");
        usuario.setEmail("carlos@test.cl");
        usuario.setDireccion("Avenida Siempre Viva 123");
        usuario.setEdad(31);
        usuario.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        usuario.setActivo(true);

        when(usuarioRepository.findByEmailAndActivo("carlos@test.cl", true))
                .thenReturn(List.of(usuario));

        List<UsuarioDTO> resultado = usuarioService.buscarPorEmailYActivo("carlos@test.cl", true);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("carlos@test.cl", resultado.get(0).getEmail());
        assertTrue(resultado.get(0).getActivo());

        verify(usuarioRepository, times(1)).findByEmailAndActivo("carlos@test.cl", true);
    }
}