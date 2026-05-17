package com.fullstackev2.sucursales.service;

import com.fullstackev2.sucursales.dto.RegionRequestDTO;
import com.fullstackev2.sucursales.dto.RegionResponseDTO;
import com.fullstackev2.sucursales.mapper.RegionMapper;
import com.fullstackev2.sucursales.model.Region;
import com.fullstackev2.sucursales.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public List<RegionResponseDTO> listarTodos() {
        log.info("[RegionService] Listando todas las regiones");
        return regionRepository.findAll().stream()
                .map(regionMapper::toResponseDTO).toList();
    }

    public RegionResponseDTO buscarPorId(Integer id) {
        log.info("[RegionService] Buscando region id={}", id);
        Optional<Region> optional = regionRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[RegionService] Region id={} no encontrada", id);
            return null;
        }
        return regionMapper.toResponseDTO(optional.get());
    }

    public RegionResponseDTO guardar(RegionRequestDTO dto) {
        log.info("[RegionService] Creando region nombre={}", dto.getNombre());
        try {
            Region guardada = regionRepository.save(regionMapper.toModel(dto));
            log.info("[RegionService] Region creada id={}", guardada.getRegionId());
            return regionMapper.toResponseDTO(guardada);
        } catch (Exception e) {
            log.error("[RegionService] Error al crear region: {}", e.getMessage());
            throw new RuntimeException("Error al crear region", e);
        }
    }

    public RegionResponseDTO actualizar(Integer id, RegionRequestDTO dto) {
        log.info("[RegionService] Actualizando region id={}", id);
        Optional<Region> optional = regionRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[RegionService] Region id={} no existe", id);
            return null;
        }
        Region existente = optional.get();
        existente.setNombre(dto.getNombre());
        existente.setCodigo(dto.getCodigo());
        existente.setDescripcion(dto.getDescripcion());
        existente.setNumeroComunas(dto.getNumeroComunas());
        existente.setActivo(dto.getActivo());
        existente.setFechaCreacion(dto.getFechaCreacion());
        log.info("[RegionService] Region id={} actualizada", id);
        return regionMapper.toResponseDTO(regionRepository.save(existente));
    }

    public boolean eliminar(Integer id) {
        log.info("[RegionService] Eliminando region id={}", id);
        if (!regionRepository.existsById(id)) {
            log.warn("[RegionService] Region id={} no existe", id);
            return false;
        }
        regionRepository.deleteById(id);
        log.info("[RegionService] Region id={} eliminada", id);
        return true;
    }

    public List<RegionResponseDTO> buscarPorNombre(String nombre) {
        log.info("[RegionService] Buscando regiones con nombre={}", nombre);
        return regionRepository.findByNombre(nombre).stream()
                .map(regionMapper::toResponseDTO).toList();
    }
}