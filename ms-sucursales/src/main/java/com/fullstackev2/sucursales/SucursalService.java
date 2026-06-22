package com.fullstackev2.sucursales.service;

import com.fullstackev2.sucursales.dto.SucursalRequestDTO;
import com.fullstackev2.sucursales.dto.SucursalResponseDTO;
import com.fullstackev2.sucursales.mapper.SucursalMapper;
import com.fullstackev2.sucursales.model.Region;
import com.fullstackev2.sucursales.model.Sucursal;
import com.fullstackev2.sucursales.repository.RegionRepository;
import com.fullstackev2.sucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SucursalService {

    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;
    private final RegionRepository regionRepository;

    public List<SucursalResponseDTO> listarTodos() {
        log.info("[SucursalService] Listando todas las sucursales");
        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::toResponseDTO).toList();
    }

    public SucursalResponseDTO buscarPorId(Integer id) {
        log.info("[SucursalService] Buscando sucursal id={}", id);
        Optional<Sucursal> optional = sucursalRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[SucursalService] Sucursal id={} no encontrada", id);
            return null;
        }
        return sucursalMapper.toResponseDTO(optional.get());
    }

    public SucursalResponseDTO guardar(SucursalRequestDTO dto) {
        log.info("[SucursalService] Creando sucursal nombre={}", dto.getNombre());
        try {
            Optional<Region> region = regionRepository.findById(dto.getRegionId());
            if (region.isEmpty()) {
                throw new IllegalArgumentException("Region no encontrada con id: " + dto.getRegionId());
            }
            Sucursal guardada = sucursalRepository.save(sucursalMapper.toModel(dto, region.get()));
            log.info("[SucursalService] Sucursal creada id={}", guardada.getSucursalId());
            return sucursalMapper.toResponseDTO(guardada);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("[SucursalService] Error al crear sucursal: {}", e.getMessage());
            throw new RuntimeException("Error al crear sucursal", e);
        }
    }

    public SucursalResponseDTO actualizar(Integer id, SucursalRequestDTO dto) {
        log.info("[SucursalService] Actualizando sucursal id={}", id);
        Optional<Sucursal> optional = sucursalRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[SucursalService] Sucursal id={} no existe", id);
            return null;
        }
        Optional<Region> region = regionRepository.findById(dto.getRegionId());
        if (region.isEmpty()) {
            throw new IllegalArgumentException("Region no encontrada con id: " + dto.getRegionId());
        }
        Sucursal existente = optional.get();
        existente.setNombre(dto.getNombre());
        existente.setDireccion(dto.getDireccion());
        existente.setTelefono(dto.getTelefono());
        existente.setCapacidad(dto.getCapacidad());
        existente.setActivo(dto.getActivo());
        existente.setFechaApertura(dto.getFechaApertura());
        existente.setRegion(region.get());
        log.info("[SucursalService] Sucursal id={} actualizada", id);
        return sucursalMapper.toResponseDTO(sucursalRepository.save(existente));
    }

    public boolean eliminar(Integer id) {
        log.info("[SucursalService] Eliminando sucursal id={}", id);
        if (!sucursalRepository.existsById(id)) {
            log.warn("[SucursalService] Sucursal id={} no existe", id);
            return false;
        }
        sucursalRepository.deleteById(id);
        log.info("[SucursalService] Sucursal id={} eliminada", id);
        return true;
    }

    public List<SucursalResponseDTO> buscarPorNombreRegion(String nombreRegion) {
        log.info("[SucursalService] Buscando sucursales por region={}", nombreRegion);
        return sucursalRepository.findByNombreRegion(nombreRegion).stream()
                .map(sucursalMapper::toResponseDTO).toList();
    }
}