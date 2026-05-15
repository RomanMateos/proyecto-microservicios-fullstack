package com.fullstackev2.reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // <- IMPORTANTE

@SpringBootApplication
@EnableFeignClients // <- IMPORTANTE
public class ReportesApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReportesApplication.class, args);
	}
}
