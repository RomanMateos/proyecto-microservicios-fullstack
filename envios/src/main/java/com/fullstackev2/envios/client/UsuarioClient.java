package com.fullstackev2.envios.client;

import com.fullstackev2.envios.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="usuario-service",url="http://localhost:8081")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDTO obtenerUsuario(@PathVariable Integer id);
}
