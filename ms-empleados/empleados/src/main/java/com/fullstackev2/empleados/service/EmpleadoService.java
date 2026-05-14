package com.fullstackev2.empleados.service;

import com.fullstackev2.empleados.dto.EmpleadoRequestDTO;
import com.fullstackev2.empleados.dto.EmpleadoResponseDTO;
import com.fullstackev2.empleados.mapper.EmpleadoMapper;
import com.fullstackev2.empleados.model.Empleado;
import com.fullstackev2.empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;

    public List<EmpleadoResponseDTO> listarTodos() {
        log.info("[EmpleadoService] Listando todos los empleados");
        return empleadoRepository.findAll().stream()
                .map(empleadoMapper::toResponseDTO).toList();
    }

    public EmpleadoResponseDTO buscarPorId(Integer id) {
        log.info("[EmpleadoService] Buscando empleado id={}", id);
        Optional<Empleado> optional = empleadoRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[EmpleadoService] Empleado id={} no encontrado", id);
            return null;
        }
        return empleadoMapper.toResponseDTO(optional.get());
    }

    public EmpleadoResponseDTO guardar(EmpleadoRequestDTO dto) {
        log.info("[EmpleadoService] Creando empleado email={}", dto.getEmail());
        try {
            if (empleadoRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("El email ya está registrado: " + dto.getEmail());
            }
            Empleado guardado = empleadoRepository.save(empleadoMapper.toModel(dto));
            log.info("[EmpleadoService] Empleado creado id={}", guardado.getEmpleadoId());
            return empleadoMapper.toResponseDTO(guardado);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("[EmpleadoService] Error al crear empleado: {}", e.getMessage());
            throw new RuntimeException("Error al crear empleado", e);
        }
    }

    public EmpleadoResponseDTO actualizar(Integer id, EmpleadoRequestDTO dto) {
        log.info("[EmpleadoService] Actualizando empleado id={}", id);
        Optional<Empleado> optional = empleadoRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("[EmpleadoService] Empleado id={} no existe", id);
            return null;
        }
        Empleado existente = optional.get();
        existente.setNombre(dto.getNombre());
        existente.setEmail(dto.getEmail());
        existente.setCargo(dto.getCargo());
        existente.setSalario(dto.getSalario());
        existente.setActivo(dto.getActivo());
        existente.setFechaIngreso(dto.getFechaIngreso());
        existente.setSucursalId(dto.getSucursalId());
        log.info("[EmpleadoService] Empleado id={} actualizado", id);
        return empleadoMapper.toResponseDTO(empleadoRepository.save(existente));
    }

    public boolean eliminar(Integer id) {
        log.info("[EmpleadoService] Eliminando empleado id={}", id);
        if (!empleadoRepository.existsById(id)) {
            log.warn("[EmpleadoService] Empleado id={} no existe", id);
            return false;
        }
        empleadoRepository.deleteById(id);
        log.info("[EmpleadoService] Empleado id={} eliminado", id);
        return true;
    }

    public List<EmpleadoResponseDTO> buscarPorSucursalYAnio(Integer sucursalId, Integer anio) {
        log.info("[EmpleadoService] Buscando empleados sucursal={} anio={}", sucursalId, anio);
        return empleadoRepository.findBySucursalIdAndAnioIngreso(sucursalId, anio)
                .stream().map(empleadoMapper::toResponseDTO).toList();
    }
}