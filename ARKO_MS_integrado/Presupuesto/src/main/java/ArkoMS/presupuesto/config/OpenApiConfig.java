package ArkoMS.presupuesto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI presupuestoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Presupuesto")
                        .description("Gestión de presupuestos asociados a obras de construcción.")
                        .version("1.0"));
    }
}
