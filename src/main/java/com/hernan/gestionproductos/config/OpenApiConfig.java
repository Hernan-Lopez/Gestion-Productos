package com.hernan.gestionproductos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(
        title = "API Documentation", 
        version = "1.0", 
        contact = @Contact(name = "Santander", email = "support@santander.cl"),
        license = @License(name = "Chile API Terms and License", url = "http://www.santander.cl/terms-of-service.html")
    ),
    security = @SecurityRequirement(name = "basicAuth")
)
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Autowired Environment env) {
        return new OpenAPI()
            .info(
                new io.swagger.v3.oas.models.info.Info()
                    .title(env.getProperty("springdoc.docs.title", "API Documentation"))
                    .description(env.getProperty("springdoc.docs.description", "Descripción de la API"))
                    .version(env.getProperty("springdoc.docs.version", "1.0"))
                    .termsOfService(env.getProperty("springdoc.docs.terms", "http://www.santander.cl/terms-of-service.html"))
                    .contact(
                        new io.swagger.v3.oas.models.info.Contact()
                            .name("Santander")
                            .email("support@santander.cl")
                    )
                    .license(
                        new io.swagger.v3.oas.models.info.License()
                            .name("Chile API Terms and License")
                            .url("http://www.santander.cl/terms-of-service.html")
                    )
            )
            .components(
                new Components()
                    .addSchemas("ErrorResponse", createErrorResponseSchema())
                    .addSecuritySchemes("basicAuth", createBasicAuthScheme())
            );
    }

    private Schema<?> createErrorResponseSchema() {
        return new Schema<>()
            .addProperty("code", new Schema<>().type("integer").example(400).description("Código HTTP del error"))
            .addProperty("message", new Schema<>().type("string").example("Bad Request").description("Mensaje corto del error"))
            .addProperty("level", new Schema<>().type("string").example("Warning").description("Nivel de severidad del error"))
            .addProperty("description", new Schema<>().type("string").example("Descripción detallada del error"));
    }

    private SecurityScheme createBasicAuthScheme() {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("basic")
            .description("Autenticación básica para acceder a los endpoints protegidos.");
    }
}
