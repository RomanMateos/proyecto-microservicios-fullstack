package com.fullstackev2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SucursalesApplication {
	public static void main(String[] args) {
		SpringApplication.run(SucursalesApplication.class, args);
	}
}