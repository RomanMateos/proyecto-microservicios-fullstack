package com.fullstackev2.envios.service;

import com.fullstackev2.envios.dto.SeguimientoDTO;
import com.fullstackev2.envios.mapper.SeguimientoMapper;
import com.fullstackev2.envios.model.Envio;
import com.fullstackev2.envios.model.Seguimiento;
import com.fullstackev2.envios.repository.EnvioRepository;
import com.fullstackev2.envios.repository.SeguimientoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeguimientoService {

    @Autowired
    SeguimientoRepository seguimientoRepository;
    @Autowired
    EnvioRepository envioRepository;
    public List<SeguimientoDTO> listarSeguimientos(){
        return seguimientoRepository.findAll()
                .stream()
                .map(SeguimientoMapper::toDTO)
                .collect(Collectors.toList());
    }
    public SeguimientoDTO guardar(SeguimientoDTO dto){
        Envio envio = envioRepository.findById(dto.getEnvioId())
                .orElseThrow(() -> new RuntimeException("Envio no encontrado"));
        Seguimiento seguimiento = SeguimientoMapper.toEntity(dto);
        seguimiento.setEnvio(envio);
        Seguimiento guardado = seguimientoRepository.save(seguimiento);
        return SeguimientoMapper.toDTO(guardado);
    }
    public Optional<SeguimientoDTO> buscarPorId(Integer id){
        return seguimientoRepository.findById(id)
                .map(SeguimientoMapper::toDTO);
    }

    public Optional<SeguimientoDTO> actualizarPorId(Integer id, SeguimientoDTO dto){
        Seguimiento seg = seguimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado"));
        Envio envio = envioRepository.findById(dto.getEnvioId())
                .orElseThrow(() -> new RuntimeException("Envio no encontrado"));

        seg.setEstado(dto.getEstado());
        seg.setDireccionEnvio(dto.getDireccionEnvio());
        seg.setFechaDespacho(LocalDate.now());
        seg.setActivo(dto.getActivo());
        seg.setAncho(dto.getAncho());
        seg.setAlto(dto.getAlto());
        seg.setEntregado(dto.getEntregado());
        seg.setEnvio(envio);

        Seguimiento actualizado = seguimientoRepository.save(seg);
        return Optional.of(SeguimientoMapper.toDTO(actualizado));
    }
    public boolean eliminarPorId(Integer id){
        if(seguimientoRepository.existsById(id)){
            seguimientoRepository.deleteById(id);
            return true;
        }
        return false;
    }



}
