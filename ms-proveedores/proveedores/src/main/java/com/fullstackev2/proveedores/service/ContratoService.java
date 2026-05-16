package com.fullstackev2.proveedores.service;

import com.fullstackev2.proveedores.dto.ContratoRequestDTO;
import com.fullstackev2.proveedores.dto.ContratoResponseDTO;
import com.fullstackev2.proveedores.mapper.ContratoMapper;
import com.fullstackev2.proveedores.model.Contrato;
import com.fullstackev2.proveedores.model.Proveedor;
import com.fullstackev2.proveedores.repository.ContratoRepository;
import com.fullstackev2.proveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final ContratoMapper contratoMapper;
    private final ProveedorRepository proveedorRepository;

    public List<ContratoResponseDTO> listarTodos() {
        log.info("[ContratoService] Listando todos los contratos");
        return contratoRepository.findAll()
                .stream()
                .map(contratoMapper::toResponseDTO)
                .toList();
    }

    public ContratoResponseDTO buscarPorId(Integer id) {
        log.info("[ContratoService] Buscando contrato id={}", id);
        Optional<Contrato> optional = contratoRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[ContratoService] Contrato id={} no encontrado", id);
            return null;
        }
        return contratoMapper.toResponseDTO(optional.get());
    }

    public ContratoResponseDTO guardar(ContratoRequestDTO dto) {
        log.info("[ContratoService] Creando contrato para proveedor id={}", dto.getProveedorId());
        try {
            Optional<Proveedor> proveedor = proveedorRepository.findById(dto.getProveedorId());
            if (proveedor.isEmpty()) {
                log.warn("[ContratoService] Proveedor id={} no existe", dto.getProveedorId());
                throw new IllegalArgumentException("Proveedor no encontrado con id: " + dto.getProveedorId());
            }
            Contrato guardado = contratoRepository.save(contratoMapper.toModel(dto, proveedor.get()));
            log.info("[ContratoService] Contrato creado con id={}", guardado.getContratoId());
            return contratoMapper.toResponseDTO(guardado);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("[ContratoService] Error al crear contrato: {}", e.getMessage());
            throw new RuntimeException("Error al crear contrato", e);
        }
    }

    public ContratoResponseDTO actualizar(Integer id, ContratoRequestDTO dto) {
        log.info("[ContratoService] Actualizando contrato id={}", id);
        Optional<Contrato> optional = contratoRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[ContratoService] Contrato id={} no existe", id);
            return null;
        }
        Optional<Proveedor> proveedor = proveedorRepository.findById(dto.getProveedorId());
        if (proveedor.isEmpty()) {
            throw new IllegalArgumentException("Proveedor no encontrado con id: " + dto.getProveedorId());
        }
        Contrato existente = optional.get();
        existente.setNumeroContrato(dto.getNumeroContrato());
        existente.setDescripcion(dto.getDescripcion());
        existente.setMontoTotal(dto.getMontoTotal());
        existente.setActivo(dto.getActivo());
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setFechaFin(dto.getFechaFin());
        existente.setProveedor(proveedor.get());
        log.info("[ContratoService] Contrato id={} actualizado", id);
        return contratoMapper.toResponseDTO(contratoRepository.save(existente));
    }

    public boolean eliminar(Integer id) {
        log.info("[ContratoService] Eliminando contrato id={}", id);
        if (!contratoRepository.existsById(id)) {
            log.warn("[ContratoService] Contrato id={} no existe", id);
            return false;
        }
        contratoRepository.deleteById(id);
        log.info("[ContratoService] Contrato id={} eliminado", id);
        return true;
    }
}