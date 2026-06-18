package cl.dsy1103.asistencia.config;

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
                        .title("Microservicio de Asistencia")
                        .description("Marcaje de entrada/salida de empleados y cálculo de horas trabajadas.")
                        .version("1.0"));
    }
}
