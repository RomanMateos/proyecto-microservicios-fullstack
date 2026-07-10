package com.fullstackev2.reportes.client;

import com.fullstackev2.reportes.dto.PedidoDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


// Usamos el 'name' con el nombre exacto con el que el microservicio se registra en Eureka
@FeignClient(name = "ms-pedidos")
public interface PedidoClient {
    @GetMapping("/api/v1/pedidos")
    List<PedidoDataDTO> obtenerPedidos();
}