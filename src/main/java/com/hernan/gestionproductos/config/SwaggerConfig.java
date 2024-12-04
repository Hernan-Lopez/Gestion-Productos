package com.hernan.gestionproductos.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Product Management API")
                .version("1.0")
                .description("API para gestionar productos en el sistema")
                .contact(new Contact()
                    .name("Hernán López")
                    .email("hernan@example.com")
                    .url("https://hernan.example.com")
                )
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")
                )
            )
            .externalDocs(new ExternalDocumentation()
                .description("Documentación adicional")
                .url("https://github.com/Hernan-Lopez"));
    }
}
