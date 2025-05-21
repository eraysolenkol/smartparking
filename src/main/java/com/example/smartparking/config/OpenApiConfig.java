package com.example.smartparking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "X-API-KEY";

        return new OpenAPI()
                .info(new Info()
                        .title("Smart Parking API")
                        .version("1.0.0")
                        .description(
                                "This API provides functionality for managing smart parking reservations, availability, and user interactions. All requests must include a valid `X-API-KEY` in the headers."))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description(
                                                "API key required to authorize requests. Add it to the `X-API-KEY` header.")));
    }
}
