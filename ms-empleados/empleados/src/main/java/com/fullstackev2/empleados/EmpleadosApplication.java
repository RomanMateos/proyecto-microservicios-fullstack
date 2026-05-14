package com.fullstackev2.empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmpleadosApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmpleadosApplication.class, args);
	}
}