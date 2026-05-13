package com.fullstackev2.pagos.mapper;

import com.fullstackev2.pagos.dto.PagoDTO;
import com.fullstackev2.pagos.model.Pago;

public class PagoMapper {
    public static PagoDTO toDTO(Pago pago){
        return new PagoDTO(
                pago.getId(),
                pago.getMetodoPago(),
                pago.getDetalle(),
                pago.getMontoPago(),
                pago.getFechaPago(),
                pago.getAceptado()
        );
    }
    public static Pago toEntity(PagoDTO dto){
        Pago pago = new Pago();
        pago.setId(dto.getId());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setDetalle(dto.getDetalle());
        pago.setMontoPago(dto.getMontoPago());
        pago.setFechaPago(dto.getFechaPago());
        pago.setAceptado(dto.getAceptado());
        return pago;
    }
}
