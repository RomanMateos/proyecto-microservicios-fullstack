package com.fullstackev2.ms_pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8081")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    Object getUsuarioById(@PathVariable Integer id);
}