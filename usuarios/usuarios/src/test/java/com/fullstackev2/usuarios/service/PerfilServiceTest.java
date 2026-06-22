package com.fullstackev2.usuarios.service;

import com.fullstackev2.usuarios.dto.PerfilDTO;
import com.fullstackev2.usuarios.model.Perfil;
import com.fullstackev2.usuarios.model.Usuario;
import com.fullstackev2.usuarios.repository.PerfilRepository;
import com.fullstackev2.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PerfilService perfilService;

    @Test
    void obtenerPerfiles_deberiaRetornarListaDePerfiles() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Perfil perfil = new Perfil();
        perfil.setId(1);
        perfil.setNombrePerfil("Carlos Perfil");
        perfil.setAlias("CLabbe31");
        perfil.setEmail("carlos@test.cl");
        perfil.setEdad(31);
        perfil.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        perfil.setActivo(true);
        perfil.setUsuario(usuario);

        when(perfilRepository.findAll()).thenReturn(List.of(perfil));

        List<PerfilDTO> resultado = perfilService.obtenerPerfiles();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos Perfil", resultado.get(0).getNombrePerfil());
        assertEquals(1, resultado.get(0).getUsuarioId());

        verify(perfilRepository, times(1)).findAll();
    }

    @Test
    void guardar_deberiaGuardarPerfilCuandoUsuarioExiste() {
        PerfilDTO dto = new PerfilDTO();
        dto.setNombrePerfil("Carlos Perfil");
        dto.setAlias("CLabbe31");
        dto.setEmail("carlos@test.cl");
        dto.setEdad(31);
        dto.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        dto.setActivo(true);
        dto.setUsuarioId(1);

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombreCompleto("Carlos Labbe");

        Perfil perfilGuardado = new Perfil();
        perfilGuardado.setId(1);
        perfilGuardado.setNombrePerfil("Carlos Perfil");
        perfilGuardado.setAlias("CLabbe31");
        perfilGuardado.setEmail("carlos@test.cl");
        perfilGuardado.setEdad(31);
        perfilGuardado.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        perfilGuardado.setActivo(true);
        perfilGuardado.setUsuario(usuario);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(perfilRepository.save(Mockito.any(Perfil.class))).thenReturn(perfilGuardado);

        PerfilDTO resultado = perfilService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Carlos Perfil", resultado.getNombrePerfil());
        assertEquals(1, resultado.getUsuarioId());

        verify(usuarioRepository, times(1)).findById(1);
        verify(perfilRepository, times(1)).save(Mockito.any(Perfil.class));
    }

    @Test
    void guardar_cuandoUsuarioNoExiste_deberiaLanzarExcepcion() {
        PerfilDTO dto = new PerfilDTO();
        dto.setNombrePerfil("Carlos Perfil");
        dto.setAlias("CLabbe31");
        dto.setEmail("carlos@test.cl");
        dto.setEdad(31);
        dto.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        dto.setActivo(true);
        dto.setUsuarioId(99);

        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            perfilService.guardar(dto);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());

        verify(usuarioRepository, times(1)).findById(99);
        verify(perfilRepository, never()).save(Mockito.any(Perfil.class));
    }

    @Test
    void buscarPorId_cuandoExiste_deberiaRetornarPerfil() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Perfil perfil = new Perfil();
        perfil.setId(1);
        perfil.setNombrePerfil("Carlos Perfil");
        perfil.setAlias("CLabbe31");
        perfil.setEmail("carlos@test.cl");
        perfil.setEdad(31);
        perfil.setFechaNacimiento(LocalDate.of(1995, 4, 5));
        perfil.setActivo(true);
        perfil.setUsuario(usuario);

        when(perfilRepository.findById(1)).thenReturn(Optional.of(perfil));

        Optional<PerfilDTO> resultado = perfilService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());
        assertEquals("Carlos Perfil", resultado.get().getNombrePerfil());

        verify(perfilRepository, times(1)).findById(1);
    }

    @Test
    void buscarPorId_cuandoNoExiste_deberiaRetornarOptionalVacio() {
        when(perfilRepository.findById(99)).thenReturn(Optional.empty());

        Optional<PerfilDTO> resultado = perfilService.buscarPorId(99);

        assertTrue(resultado.isEmpty());

        verify(perfilRepository, times(1)).findById(99);
    }

    @Test
    void eliminarPorId_cuandoExiste_deberiaEliminarYRetornarTrue() {
        when(perfilRepository.existsById(1)).thenReturn(true);

        boolean resultado = perfilService.eliminarPorId(1);

        assertTrue(resultado);

        verify(perfilRepository, times(1)).existsById(1);
        verify(perfilRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarPorId_cuandoNoExiste_deberiaRetornarFalse() {
        when(perfilRepository.existsById(99)).thenReturn(false);

        boolean resultado = perfilService.eliminarPorId(99);

        assertFalse(resultado);

        verify(perfilRepository, times(1)).existsById(99);
        verify(perfilRepository, never()).deleteById(99);
    }
}