package arko.cl.personal.config;

import arko.cl.personal.model.Trabajador;
import arko.cl.personal.repository.TrabajadorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TrabajadorRepository trabajadorRepository;

    public DataInitializer(TrabajadorRepository trabajadorRepository) {
        this.trabajadorRepository = trabajadorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (trabajadorRepository.count() == 0) {
            // RUN: 25.123.456-K
            Trabajador trab1 = new Trabajador();
            trab1.setNumrunTrab(25123456L);
            trab1.setDvrunTrab("K");
            trab1.setPnombreTrab("Juan");
            trab1.setSnombreTrab("Carlos");
            trab1.setPapellidoTrab("García");
            trab1.setSapellidoTrab("Rodríguez");
            trab1.setCargo("Ingeniero");
            trab1.setFono("912345678");
            trab1.setMailTrab("juan.garcia@arko.com");
            trab1.setEstado("activo");
            trabajadorRepository.save(trab1);

            // RUN: 18.456.789-5
            Trabajador trab2 = new Trabajador();
            trab2.setNumrunTrab(18456789L);
            trab2.setDvrunTrab("5");
            trab2.setPnombreTrab("María");
            trab2.setSnombreTrab("Alejandra");
            trab2.setPapellidoTrab("López");
            trab2.setSapellidoTrab("Martínez");
            trab2.setCargo("Diseñadora");
            trab2.setFono("987654321");
            trab2.setMailTrab("maria.lopez@arko.com");
            trab2.setEstado("activo");
            trabajadorRepository.save(trab2);

            // RUN: 12.123.456-K
            Trabajador trab3 = new Trabajador();
            trab3.setNumrunTrab(12123456L);
            trab3.setDvrunTrab("K");
            trab3.setPnombreTrab("Pedro");
            trab3.setSnombreTrab("Luis");
            trab3.setPapellidoTrab("Vega");
            trab3.setSapellidoTrab("Silva");
            trab3.setCargo("Técnico");
            trab3.setFono("955551234");
            trab3.setMailTrab("pedro.vega@arko.com");
            trab3.setEstado("activo");
            trabajadorRepository.save(trab3);
        }
    }
}
