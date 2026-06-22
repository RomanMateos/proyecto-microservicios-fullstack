package com.fullstackev2.productos;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableDiscoveryClient
@SpringBootApplication
public class ProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductosApplication.class, args);
	}

}
