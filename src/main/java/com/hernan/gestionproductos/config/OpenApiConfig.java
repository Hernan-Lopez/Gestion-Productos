package com.hernan.gestionproductos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.OpenAPI;

@OpenAPIDefinition(
	    info = @Info(title = "API Documentation", version = "1.0")//,
	    //security = @SecurityRequirement(name = "basicAuth")
	)
public class OpenApiConfig {
	
	@Bean
    public OpenAPI openAPI(@Autowired Environment env) {
        return new OpenAPI().info(
                new io.swagger.v3.oas.models.info.Info()
                        .title(env.getProperty("springdoc.docs.title"))
                        .description(env.getProperty("springdoc.docs.description"))
                        .version(env.getProperty("springdoc.docs.version"))
                        .termsOfService(env.getProperty("springdoc.docs.terms"))
                        .contact(
                                new io.swagger.v3.oas.models.info.Contact()
                                .name("Santander")
                        )
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Chile API Terms and License")
                                .url("http://www.santander.cl/terms-of-service.html")
                        )
        );
    }
	
}