package com.fullstackev2.pagos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Control de Pagos")
                        .version("1.0.0")
                        .description("Documentación interactiva de los endpoints del microservicio de Pagos (Rescate de Nota)"));
    }
}