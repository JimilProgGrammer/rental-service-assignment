package com.rental.setu.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for springdoc-openapi-ui starter that
 * aids in auto-generating OpenAPI Spec that can be
 * rendered via SwaggerUI.
 *
 * @author jimil
 */
@Configuration
public class SwaggerConfig {

    /**
     * Create new OpenAPI bean with basic information about
     * the service
     * @return the OpenAPI bean with basic info like title,
     * description and contact details
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Setu Rental Service APIs")
                        .description("RESTful Spring Boot service for searching, booking and viewing rental trips")
                        .contact(new Contact().name("Jimil").email("shahjimil35@gmail.com"))
                );
    }

}
