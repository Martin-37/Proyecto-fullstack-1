package arko.tickets.service;

import arko.tickets.dto.TicketRequestDTO;
import arko.tickets.dto.TicketResponseDTO;
import arko.tickets.model.Ticket;
import arko.tickets.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketResponseDTO guardar(TicketRequestDTO requestDTO) {
        validarPrioridad(requestDTO.getPrioridad());
        validarEstado(requestDTO.getEstado());

        Ticket ticket = new Ticket();
        ticket.setTitulo(requestDTO.getTitulo());
        ticket.setDescripcion(requestDTO.getDescripcion());
        ticket.setPrioridad(requestDTO.getPrioridad());
        ticket.setEstado(requestDTO.getEstado());
        ticket.setResponsable(requestDTO.getResponsable());

        Ticket guardado = ticketRepository.save(ticket);
        return mapToDTO(guardado);
    }

    public TicketResponseDTO obtenerPorId(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
        return mapToDTO(ticket);
    }

    public List<TicketResponseDTO> obtenerTodos() {
        return ticketRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TicketResponseDTO actualizar(Long id, TicketRequestDTO requestDTO) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));

        validarPrioridad(requestDTO.getPrioridad());
        validarEstado(requestDTO.getEstado());

        ticket.setTitulo(requestDTO.getTitulo());
        ticket.setDescripcion(requestDTO.getDescripcion());
        ticket.setPrioridad(requestDTO.getPrioridad());
        ticket.setEstado(requestDTO.getEstado());
        ticket.setResponsable(requestDTO.getResponsable());

        Ticket actualizado = ticketRepository.save(ticket);
        return mapToDTO(actualizado);
    }

    public void eliminar(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
        ticketRepository.delete(ticket);
    }

    public List<TicketResponseDTO> buscarPorTitulo(String titulo) {
        return ticketRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorEstado(String estado) {
        validarEstado(estado);
        return ticketRepository.findByEstado(estado).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorPrioridad(String prioridad) {
        validarPrioridad(prioridad);
        return ticketRepository.findByPrioridad(prioridad).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorResponsable(String responsable) {
        return ticketRepository.findByResponsable(responsable).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorDescripcion(String descripcion) {
        return ticketRepository.findByDescripcionContaining(descripcion).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorEstadoYPrioridad(String estado, String prioridad) {
        validarEstado(estado);
        validarPrioridad(prioridad);
        return ticketRepository.findByEstadoAndPrioridad(estado, prioridad).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorResponsableYEstado(String responsable, String estado) {
        validarEstado(estado);
        return ticketRepository.findByResponsableAndEstado(responsable, estado).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Long contarPorEstado(String estado) {
        validarEstado(estado);
        return ticketRepository.countByEstado(estado);
    }

    public Long contarPorPrioridad(String prioridad) {
        validarPrioridad(prioridad);
        return ticketRepository.countByPrioridad(prioridad);
    }

    private void validarPrioridad(String prioridad) {
        if (!prioridad.matches("^(BAJA|MEDIA|ALTA|URGENTE)$")) {
            throw new RuntimeException("Prioridad inválida: " + prioridad + ". Debe ser: BAJA, MEDIA, ALTA o URGENTE");
        }
    }

    private void validarEstado(String estado) {
        if (!estado.matches("^(ABIERTO|EN_PROCESO|PENDIENTE|RESUELTO|CERRADO)$")) {
            throw new RuntimeException("Estado inválido: " + estado + ". Debe ser: ABIERTO, EN_PROCESO, PENDIENTE, RESUELTO o CERRADO");
        }
    }

    private TicketResponseDTO mapToDTO(Ticket ticket) {
        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getTitulo(),
                ticket.getDescripcion(),
                ticket.getPrioridad(),
                ticket.getEstado(),
                ticket.getResponsable()
        );
    }
}
