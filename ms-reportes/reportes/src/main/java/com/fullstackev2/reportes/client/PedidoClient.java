package com.fullstackev2.reportes.client;

import com.fullstackev2.reportes.dto.PedidoDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-pedidos", url = "http://localhost:8081/api/v1/pedidos")
public interface PedidoClient {

    @GetMapping
    List<PedidoDataDTO> obtenerPedidosParaReporte();
}