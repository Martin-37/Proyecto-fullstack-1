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
        Ticket ticket1 = new Ticket(null, "Solicitud de documentos de proyecto",
                "Se requieren los documentos de permisos y planos del proyecto Las Condes",
                "ALTA", "ABIERTO", "Juan García");
        Ticket ticket2 = new Ticket(null, "Pago pendiente a proveedores",
                "Necesario procesar el pago a los proveedores de cemento y acero",
                "URGENTE", "EN_PROCESO", "María López");
        Ticket ticket3 = new Ticket(null, "Deuda pendiente con contratista",
                "El contratista está reclamando el pago de la segunda cuota",
                "MEDIA", "PENDIENTE", "Carlos Rodríguez");
        Ticket ticket4 = new Ticket(null, "Envío de materiales",
                "Coordinar el envío de materiales al sitio de obra en Puerto Montt",
                "MEDIA", "ABIERTO", "Ana Martínez");
        Ticket ticket5 = new Ticket(null, "Aprobación de presupuesto",
                "El cliente requiere aprobación del presupuesto adicional",
                "ALTA", "PENDIENTE", "Jorge Fernández");
        Ticket ticket6 = new Ticket(null, "Resolución de inspección",
                "Realizar inspección técnica y resolver los problemas identificados",
                "ALTA", "EN_PROCESO", "Juan García");

        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
        ticketRepository.save(ticket4);
        ticketRepository.save(ticket5);
        ticketRepository.save(ticket6);

        System.out.println("✓ Se han cargado 6 tickets de ejemplo");
    }
}
