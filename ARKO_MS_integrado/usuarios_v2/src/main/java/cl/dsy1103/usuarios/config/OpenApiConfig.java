package cl.dsy1103.usuarios.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // Información general de la API
                .info(new Info()
                        .title("Microservicio de Usuarios y Roles")
                        .version("2.0.0")
                        .description("API REST para la gestión de usuarios y roles con autenticación JWT. " +
                                "Para usar los endpoints protegidos, primero haz login en **/api/usuarios/login** " +
                                "y luego ingresa el token en el botón **Authorize**.")
                        .contact(new Contact()
                                .name("DSY1103")
                                .email("contacto@dsy1103.cl"))
                )
                // Servidor base
                .servers(List.of(
                        new Server().url("http://localhost:8088").description("Servidor local")
                ))
                // Aplica seguridad globalmente a todos los endpoints
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // Define el esquema JWT Bearer
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Ingresa el token JWT obtenido desde /api/usuarios/login. " +
                                                "Formato: Bearer <token> (solo pega el token, sin 'Bearer')")
                        )
                );
    }
}
