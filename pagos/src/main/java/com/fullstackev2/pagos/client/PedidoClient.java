package com.fullstackev2.pagos.client;

import com.fullstackev2.pagos.dto.PedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pedido-service",url="http://localhost:8084")
public interface PedidoClient {
    @GetMapping("/api/v1/pedidos/{id}/total")
    Double obtenerTotalPedido(@PathVariable ("id") Integer id);

}
