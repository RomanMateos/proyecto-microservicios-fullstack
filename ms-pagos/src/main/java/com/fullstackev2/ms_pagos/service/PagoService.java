package com.fullstackev2.ms_pagos.service;

import com.fullstackev2.ms_pagos.dto.PagoDTO;
import com.fullstackev2.ms_pagos.dto.PagoRequestDTO;
import com.fullstackev2.ms_pagos.exception.ResourceNotFoundException;
import com.fullstackev2.ms_pagos.mapper.PagoMapper;
import com.fullstackev2.ms_pagos.model.Pago;
import com.fullstackev2.ms_pagos.repository.PagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoMapper pagoMapper;

    public List<PagoDTO> obtenerTodos() {
        log.info("Obteniendo todos los pagos");
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toDTO).toList();
    }

    public PagoDTO obtenerPorId(Integer id) {
        log.info("Buscando pago con id: {}", id);
        try {
            Pago pago = pagoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Pago no encontrado con id: " + id));
            return pagoMapper.toDTO(pago);
        } catch (ResourceNotFoundException e) {
            log.error("Error: {}", e.getMessage());
            throw e;
        }
    }

    public PagoDTO guardar(PagoRequestDTO dto) {
        log.info("Guardando nuevo pago");
        return pagoMapper.toDTO(pagoRepository.save(pagoMapper.toEntity(dto)));
    }

    public PagoDTO actualizar(Integer id, PagoRequestDTO dto) {
        log.info("Actualizando pago con id: {}", id);
        try {
            Pago pago = pagoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Pago no encontrado con id: " + id));
            pago.setMetodoPago(dto.getMetodoPago());
            pago.setMonto(dto.getMonto());
            pago.setEstadoPago(dto.getEstadoPago());
            pago.setFechaPago(dto.getFechaPago());
            pago.setPagado(dto.isPagado());
            pago.setPedidoId(dto.getPedidoId());
            return pagoMapper.toDTO(pagoRepository.save(pago));
        } catch (ResourceNotFoundException e) {
            log.error("Error: {}", e.getMessage());
            throw e;
        }
    }

    public void eliminar(Integer id) {
        log.info("Eliminando pago con id: {}", id);
        try {
            pagoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Pago no encontrado con id: " + id));
            pagoRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            log.error("Error: {}", e.getMessage());
            throw e;
        }
    }
}