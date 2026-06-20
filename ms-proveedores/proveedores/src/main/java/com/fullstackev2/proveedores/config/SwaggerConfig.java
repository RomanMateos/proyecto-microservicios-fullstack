package com.fullstackev2.proveedores.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Esta clase configura la documentación Swagger de ms-proveedores
// Swagger genera automáticamente una interfaz web para probar los endpoints
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        // Título que aparece en la interfaz de Swagger
                        .title("MS-Proveedores API")
                        // Versión del microservicio
                        .version("1.0")
                        // Descripción del microservicio
                        .description("API REST para gestión de proveedores y contratos del sistema E-Commerce"));
    }
}