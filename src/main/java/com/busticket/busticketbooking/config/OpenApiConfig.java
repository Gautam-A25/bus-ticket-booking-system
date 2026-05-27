package com.busticket.busticketbooking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configures OpenAPI 3 / Swagger UI for the Bus Ticket Booking REST API.
 *
 * <p>This class applies two annotations:</p>
 * <ul>
 *   <li>{@code @OpenAPIDefinition} — sets the API title, version, and description
 *       that appear at the top of the Swagger UI page, and marks all endpoints
 *       as requiring Bearer (JWT) authentication by default.</li>
 *   <li>{@code @SecurityScheme} — registers the "bearerAuth" scheme so that Swagger UI
 *       displays an "Authorize" button where developers can paste their JWT token.</li>
 * </ul>
 *
 * <p>Swagger UI is accessible at {@code /swagger-ui.html} when the application is running.</p>
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Bus Ticket Booking API",
                version = "1.0",
                description = "REST API for bus ticket booking"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}