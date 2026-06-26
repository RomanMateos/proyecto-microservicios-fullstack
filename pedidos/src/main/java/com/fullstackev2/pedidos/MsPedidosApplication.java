package com.fullstackev2.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // <-- Asegúrate de importar esto

@SpringBootApplication
@EnableFeignClients
public class MsPedidosApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsPedidosApplication.class, args);
	}
}