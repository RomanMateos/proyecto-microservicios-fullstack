package com.fullstackev2.reportes.client;

import com.fullstackev2.reportes.dto.EnvioDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "ms-envios")
public interface EnvioClient {
    @GetMapping("/api/v1/envios")
    List<EnvioDataDTO> obtenerEnvios();
}