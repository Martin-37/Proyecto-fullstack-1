package cl.maquinaria.maquinariaPesada.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Maquinaria Pesada")
                        .description("Gestión y seguimiento de maquinaria pesada para obras de construcción.")
                        .version("1.0"));
    }
}
