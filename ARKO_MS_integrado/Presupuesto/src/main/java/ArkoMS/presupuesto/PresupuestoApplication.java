package ArkoMS.presupuesto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// Esto obliga a Spring a buscar tus controladores y las librerías de springdoc en todo el proyecto:
@ComponentScan(basePackages = {"ArkoMS.presupuesto", "org.springdoc"}) 
public class PresupuestoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresupuestoApplication.class, args);
    }
}	