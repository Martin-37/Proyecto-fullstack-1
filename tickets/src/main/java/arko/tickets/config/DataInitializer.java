package arko.tickets.config;

import arko.tickets.model.Ticket;
import arko.tickets.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TicketRepository ticketRepository;

    @Override
    public void run(String... args) throws Exception {
        if (ticketRepository.count() == 0) {
            Ticket ticket1 = new Ticket();
            ticket1.setTitulo("Reparación de tuberías en sector norte");
            ticket1.setDescripcion("Se requiere reparación urgente de tuberías dañadas en el sector norte del proyecto, afecta a 5 propiedades");
            ticket1.setPrioridad("URGENTE");
            ticket1.setEstado("ABIERTO");
            ticket1.setResponsable("Vicente Sandoval");

            Ticket ticket2 = new Ticket();
            ticket2.setTitulo("Instalación de electricidad en bloque A");
            ticket2.setDescripcion("Completar la instalación eléctrica en el bloque A según especificaciones técnicas y normas de seguridad");
            ticket2.setPrioridad("ALTA");
            ticket2.setEstado("EN_PROCESO");
            ticket2.setResponsable("Juan Pérez");

            Ticket ticket3 = new Ticket();
            ticket3.setTitulo("Revisión de documentación legal");
            ticket3.setDescripcion("Revisar y validar toda la documentación legal de los permisos de construcción para el segundo trimestre");
            ticket3.setPrioridad("MEDIA");
            ticket3.setEstado("PENDIENTE");
            ticket3.setResponsable("María González");

            Ticket ticket4 = new Ticket();
            ticket4.setTitulo("Compra de materiales de construcción");
            ticket4.setDescripcion("Adquirir cemento, varillas y otros materiales necesarios para la siguiente etapa constructiva");
            ticket4.setPrioridad("ALTA");
            ticket4.setEstado("EN_PROCESO");
            ticket4.setResponsable("Carlos López");

            Ticket ticket5 = new Ticket();
            ticket5.setTitulo("Pintura de áreas comunes");
            ticket5.setDescripcion("Pintar pasillos, escaleras y áreas comunes con colores especificados en el proyecto arquitectónico");
            ticket5.setPrioridad("BAJA");
            ticket5.setEstado("RESUELTO");
            ticket5.setResponsable("Roberto Díaz");

            Ticket ticket6 = new Ticket();
            ticket6.setTitulo("Inspección final del proyecto");
            ticket6.setDescripcion("Realizar inspección exhaustiva de toda la obra antes de la entrega final a los clientes");
            ticket6.setPrioridad("ALTA");
            ticket6.setEstado("CERRADO");
            ticket6.setResponsable("Vicente Sandoval");

            ticketRepository.save(ticket1);
            ticketRepository.save(ticket2);
            ticketRepository.save(ticket3);
            ticketRepository.save(ticket4);
            ticketRepository.save(ticket5);
            ticketRepository.save(ticket6);
        }
    }
}
