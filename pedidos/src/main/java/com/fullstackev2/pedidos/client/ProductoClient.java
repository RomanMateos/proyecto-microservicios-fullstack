package com.fullstackev2.pedidos.client;

import com.fullstackev2.pedidos.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-productos", url = "http://localhost:8082")
public interface ProductoClient {

    @GetMapping("/api/v1/productos/{id}")
    ProductoDTO getProductoById(@PathVariable Integer id);

    @GetMapping("/api/v1/productos/{productoId}")
    ProductoDTO obtenerProductoPorId(@PathVariable Integer productoId); // <-- Y el @PathVariable
}