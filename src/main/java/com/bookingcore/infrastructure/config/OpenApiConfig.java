package com.bookingcore.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BookingCore API")
                        .version("1.0.0")
                        .description("Domain-Driven RESTful scheduling and reservation engine.")
                        .contact(new Contact()
                                .name("Jesús Emmanuel Jiménez Carlos")
                                .email("contact@jimenezcarlos.com")));
    }
}
