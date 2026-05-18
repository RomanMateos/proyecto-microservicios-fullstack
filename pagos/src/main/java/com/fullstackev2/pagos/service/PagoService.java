package com.fullstackev2.pagos.service;

import com.fullstackev2.pagos.client.PedidoClient;
import com.fullstackev2.pagos.dto.PagoDTO;
import com.fullstackev2.pagos.dto.PedidoDTO;
import com.fullstackev2.pagos.mapper.PagoMapper;
import com.fullstackev2.pagos.model.Pago;
import com.fullstackev2.pagos.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    PedidoClient pedidoClient;
    public List<PagoDTO> obtenerTotalPedidos(){
        return pagoRepository.findAll()
                .stream()
                .map(PagoMapper::toDTO)
                .collect(Collectors.toList());
    }
    public PagoDTO guardar(PagoDTO dto) {
        Double totalPedido = pedidoClient.obtenerTotalPedido(dto.getPedidoId());

        if (totalPedido == null) {
            throw new RuntimeException("Pedido no encontrado");}
        Pago pago = PagoMapper.toEntity(dto);
        pago.setMontoPago(totalPedido);
        Pago guardado =  pagoRepository.save(pago);

        return PagoMapper.toDTO(guardado);
    }
    public Optional<PagoDTO> buscarPorId(Integer id) {
        return pagoRepository.findById(id)
                .map(PagoMapper::toDTO);
    }
    public Optional<PagoDTO> actualizarPorId(Integer id, PagoDTO dto) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        Double totalPedido = pedidoClient.obtenerTotalPedido(dto.getPedidoId());
        if (totalPedido == null) {
            throw new RuntimeException("Pago no encontrado");
        }
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setDetalle(dto.getDetalle());
        pago.setMontoPago(totalPedido);
        pago.setFechaPago(dto.getFechaPago());
        pago.setAceptado(dto.getAceptado());

        Pago actualizado = pagoRepository.save(pago);
        return Optional.of(PagoMapper.toDTO(actualizado));
    }
    public Boolean eliminarPorId(Integer id) {
        if(pagoRepository.existsById(id)){
            pagoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Double obtenerTotalPedido(Integer pedidoId){
        return pedidoClient.obtenerTotalPedido(pedidoId);
    }
    public List<PagoDTO> buscarPagos(Double monto, Boolean aceptado){
        return pagoRepository.buscarPagosPorMontoYEstado(monto,aceptado)
                .stream()
                .map(PagoMapper::toDTO)
                .collect(Collectors.toList());
    }
    


}
