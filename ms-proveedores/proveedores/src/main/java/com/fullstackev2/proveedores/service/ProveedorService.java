package com.fullstackev2.proveedores.service;

import com.fullstackev2.proveedores.dto.ProveedorRequestDTO;
import com.fullstackev2.proveedores.dto.ProveedorResponseDTO;
import com.fullstackev2.proveedores.mapper.ProveedorMapper;
import com.fullstackev2.proveedores.model.Proveedor;
import com.fullstackev2.proveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;

    public List<ProveedorResponseDTO> listarTodos() {
        log.info("[ProveedorService] Listando todos los proveedores");
        return proveedorRepository.findAll()
                .stream()
                .map(proveedorMapper::toResponseDTO)
                .toList();
    }

    public List<ProveedorResponseDTO> listarActivosOrdenados() {
        log.info("[ProveedorService] Listando proveedores activos ordenados alfabeticamente");
        return proveedorRepository.findAllActivosOrdenados()
                .stream()
                .map(proveedorMapper::toResponseDTO)
                .toList();
    }

    public ProveedorResponseDTO buscarPorId(Integer id) {
        log.info("[ProveedorService] Buscando proveedor con id={}", id);
        Optional<Proveedor> optional = proveedorRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[ProveedorService] Proveedor id={} no encontrado", id);
            return null;
        }
        return proveedorMapper.toResponseDTO(optional.get());
    }

    public ProveedorResponseDTO guardar(ProveedorRequestDTO dto) {
        log.info("[ProveedorService] Creando proveedor con email={}", dto.getEmail());
        try {
            if (proveedorRepository.existsByEmail(dto.getEmail())) {
                log.warn("[ProveedorService] Email {} ya existe", dto.getEmail());
                throw new IllegalArgumentException("El email ya está registrado: " + dto.getEmail());
            }
            Proveedor guardado = proveedorRepository.save(proveedorMapper.toModel(dto));
            log.info("[ProveedorService] Proveedor creado con id={}", guardado.getProveedorId());
            return proveedorMapper.toResponseDTO(guardado);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("[ProveedorService] Error al crear proveedor: {}", e.getMessage());
            throw new RuntimeException("Error al crear proveedor", e);
        }
    }

    public ProveedorResponseDTO actualizar(Integer id, ProveedorRequestDTO dto) {
        log.info("[ProveedorService] Actualizando proveedor id={}", id);
        Optional<Proveedor> optional = proveedorRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[ProveedorService] Proveedor id={} no existe", id);
            return null;
        }
        Proveedor existente = optional.get();
        existente.setNombre(dto.getNombre());
        existente.setEmail(dto.getEmail());
        existente.setTelefono(dto.getTelefono());
        existente.setAniosExperiencia(dto.getAniosExperiencia());
        existente.setActivo(dto.getActivo());
        existente.setFechaRegistro(dto.getFechaRegistro());
        Proveedor actualizado = proveedorRepository.save(existente);
        log.info("[ProveedorService] Proveedor id={} actualizado", id);
        return proveedorMapper.toResponseDTO(actualizado);
    }

    public boolean eliminar(Integer id) {
        log.info("[ProveedorService] Eliminando proveedor id={}", id);
        if (!proveedorRepository.existsById(id)) {
            log.warn("[ProveedorService] Proveedor id={} no existe", id);
            return false;
        }
        proveedorRepository.deleteById(id);
        log.info("[ProveedorService] Proveedor id={} eliminado", id);
        return true;
    }
}