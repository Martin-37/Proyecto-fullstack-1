package arko.comentarios.config;

import arko.comentarios.model.ComentarioTicket;
import arko.comentarios.repository.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ComentarioRepository comentarioRepository;

    @Override
    public void run(String... args) {
        if (comentarioRepository.count() == 0) {


            ComentarioTicket c1 = new ComentarioTicket();
            c1.setTicketId(1L);
            c1.setAutorRun(25123456L);
            c1.setMensaje("Inspeccioné el sector norte, confirmo daño en 3 tuberías. Solicito equipo de obreros para mañana.");
            comentarioRepository.save(c1);

            ComentarioTicket c2 = new ComentarioTicket();
            c2.setTicketId(2L);
            c2.setAutorRun(18456789L);
            c2.setMensaje("Planos eléctricos del bloque A actualizados y enviados al equipo en terreno.");
            comentarioRepository.save(c2);


            ComentarioTicket c3 = new ComentarioTicket();
            c3.setTicketId(3L);
            c3.setAutorRun(12123456L);
            c3.setMensaje("Faltan 2 permisos municipales por adjuntar antes de cerrar la revisión.");
            comentarioRepository.save(c3);

            System.out.println(">>> DataInitializer: 3 comentarios de prueba cargados correctamente.");
        }
    }
}
