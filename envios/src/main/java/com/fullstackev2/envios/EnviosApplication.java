package com.fullstackev2.envios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EnviosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnviosApplication.class, args);
	}

}
