package com.fullstackev2.empleados.mapper;

import com.fullstackev2.empleados.dto.EmpleadoRequestDTO;
import com.fullstackev2.empleados.dto.EmpleadoResponseDTO;
import com.fullstackev2.empleados.model.Empleado;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoMapper {

    public EmpleadoResponseDTO toResponseDTO(Empleado e) {
        if (e == null) return null;
        EmpleadoResponseDTO dto = new EmpleadoResponseDTO();
        dto.setEmpleadoId(e.getEmpleadoId());
        dto.setNombre(e.getNombre());
        dto.setEmail(e.getEmail());
        dto.setCargo(e.getCargo());
        dto.setSalario(e.getSalario());
        dto.setActivo(e.isActivo());
        dto.setFechaIngreso(e.getFechaIngreso());
        dto.setSucursalId(e.getSucursalId());
        return dto;
    }

    public Empleado toModel(EmpleadoRequestDTO dto) {
        if (dto == null) return null;
        Empleado e = new Empleado();
        e.setNombre(dto.getNombre());
        e.setEmail(dto.getEmail());
        e.setCargo(dto.getCargo());
        e.setSalario(dto.getSalario());
        e.setActivo(dto.getActivo());
        e.setFechaIngreso(dto.getFechaIngreso());
        e.setSucursalId(dto.getSucursalId());
        return e;
    }
}