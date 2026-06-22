package com.fullstackev2.usuarios.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Api 2026 Manejo usuario/perfil")
                        .version("1.0")
                        .description("Documentacion de la creacion de perfiles mediante usuarios")
                );
    }
}
