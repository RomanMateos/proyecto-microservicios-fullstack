package com.fullstackev2.envios.service;

import com.fullstackev2.envios.client.PedidoClient;
import com.fullstackev2.envios.client.UsuarioClient;
import com.fullstackev2.envios.dto.EnvioDTO;
import com.fullstackev2.envios.dto.PedidoDTO;
import com.fullstackev2.envios.dto.UsuarioDTO;
import com.fullstackev2.envios.mapper.EnvioMapper;
import com.fullstackev2.envios.model.Envio;
import com.fullstackev2.envios.repository.EnvioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class EnvioService {
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private PedidoClient pedidoClient;
    @Autowired
    private UsuarioClient usuarioClient;
    public List<EnvioDTO> listarEnvio(){
        log.info("[Envio Service] Iniciando listar envio");
        return envioRepository.findAll()
                .stream()
                .map(EnvioMapper::toDTO)
                .collect(Collectors.toList());
    }
    public EnvioDTO guardar(EnvioDTO dto){
        log.info("[Envio Service] Iniciando guardar envio");
        PedidoDTO pedido = pedidoClient.obtenerPedido(dto.getPedidoId());
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId());

        if(pedido == null){throw new RuntimeException("Pedido no encontrado");}
        if(usuario == null){throw new RuntimeException("Usuario no encontrado");}
        Envio envio = EnvioMapper.toEntity(dto);

        Envio guardado = envioRepository.save(envio);
        return EnvioMapper.toDTO(guardado);
    }
    public Optional<EnvioDTO> buscarPorId(Integer id){
        log.info("[Envio Service] Iniciando buscar envio");
        return envioRepository.findById(id)
                .map(EnvioMapper::toDTO);
    }
    public Optional<EnvioDTO> actualizarPorId(Integer id,EnvioDTO dto){
        log.info("[Envio Service] Iniciando actualizar envio");
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envio no encontrado"));
        PedidoDTO pedido = pedidoClient.obtenerPedido(dto.getPedidoId());
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(dto.getUsuarioId());
        if(pedido == null){throw new RuntimeException("Pedido no encontrado");}
        if(usuario == null){throw new RuntimeException("Usuario no encontrado");}
        envio.setDireccionEnvio(dto.getDireccionEnvio());
        envio.setNroSeguimiento(dto.getNroSeguimiento());
        envio.setCostoEnvio(dto.getCostoEnvio());
        envio.setFechaEnvio(dto.getFechaEnvio());
        envio.setActivo(dto.getActivo());
        envio.setPedidoId(dto.getPedidoId());
        envio.setUsuarioId(dto.getUsuarioId());

        Envio actualizado = envioRepository.save(envio);
        return Optional.of(EnvioMapper.toDTO(actualizado));

    }
    public Boolean eliminarPorId(Integer id){
        log.info("[Envio Service] Iniciando eliminar envio");
        if(envioRepository.existsById(id)){
            envioRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<EnvioDTO> buscarNoEntregadosPorRango(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("[Envio Service] Iniciando listar envio");
        return envioRepository.buscarEnviosNoEntregadosPorRangoFechas(fechaInicio, fechaFin)
                .stream()
                .map(EnvioMapper::toDTO)
                .collect(Collectors.toList());
    }


}
