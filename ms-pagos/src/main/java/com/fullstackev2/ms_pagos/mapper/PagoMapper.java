package com.fullstackev2.ms_pagos.mapper;

import com.fullstackev2.ms_pagos.dto.PagoDTO;
import com.fullstackev2.ms_pagos.dto.PagoRequestDTO;
import com.fullstackev2.ms_pagos.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoDTO toDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setMonto(pago.getMonto());
        dto.setEstadoPago(pago.getEstadoPago());
        dto.setFechaPago(pago.getFechaPago());
        dto.setPagado(pago.isPagado());
        dto.setPedidoId(pago.getPedidoId());
        return dto;
    }

    public Pago toEntity(PagoRequestDTO dto) {
        Pago pago = new Pago();
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setMonto(dto.getMonto());
        pago.setEstadoPago(dto.getEstadoPago());
        pago.setFechaPago(dto.getFechaPago());
        pago.setPagado(dto.isPagado());
        pago.setPedidoId(dto.getPedidoId());
        return pago;
    }
}