package com.fullstackev2.reportes.client;

import com.fullstackev2.reportes.dto.PagoDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-pagos")
public interface PagoClient {
    @GetMapping("/api/v1/pagos")
    List<PagoDataDTO> obtenerPagos();
}