package arko.tickets.service;

import arko.tickets.dto.TicketRequestDTO;
import arko.tickets.dto.TicketResponseDTO;
import arko.tickets.model.Ticket;
import arko.tickets.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    private TicketResponseDTO mapToDTO(Ticket ticket) {
        return new TicketResponseDTO(ticket.getId(), ticket.getTitulo(), ticket.getDescripcion(),
                ticket.getPrioridad(), ticket.getEstado(), ticket.getResponsable());
    }

    public List<TicketResponseDTO> obtenerTodos() {
        return ticketRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<TicketResponseDTO> obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID del ticket debe ser mayor a 0");
        }
        return ticketRepository.findById(id).map(this::mapToDTO);
    }

    public TicketResponseDTO guardar(TicketRequestDTO dto) {
        validarPrioridad(dto.getPrioridad());
        validarEstado(dto.getEstado());
        Ticket ticket = new Ticket(null, dto.getTitulo(), dto.getDescripcion(),
                dto.getPrioridad(), dto.getEstado(), dto.getResponsable());
        return mapToDTO(ticketRepository.save(ticket));
    }

    public Optional<TicketResponseDTO> actualizar(Long id, TicketRequestDTO dto) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID del ticket debe ser mayor a 0");
        }
        validarPrioridad(dto.getPrioridad());
        validarEstado(dto.getEstado());
        return ticketRepository.findById(id).map(existente -> {
            existente.setTitulo(dto.getTitulo());
            existente.setDescripcion(dto.getDescripcion());
            existente.setPrioridad(dto.getPrioridad());
            existente.setEstado(dto.getEstado());
            existente.setResponsable(dto.getResponsable());
            return mapToDTO(ticketRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID del ticket debe ser mayor a 0");
        }
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Ticket no encontrado con id: " + id);
        }
        ticketRepository.deleteById(id);
    }

    public List<TicketResponseDTO> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RuntimeException("El título de búsqueda no puede estar vacío");
        }
        return ticketRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new RuntimeException("El estado no puede estar vacío");
        }
        validarEstado(estado);
        return ticketRepository.findByEstado(estado).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorPrioridad(String prioridad) {
        if (prioridad == null || prioridad.trim().isEmpty()) {
            throw new RuntimeException("La prioridad no puede estar vacía");
        }
        validarPrioridad(prioridad);
        return ticketRepository.findByPrioridad(prioridad).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorResponsable(String responsable) {
        if (responsable == null || responsable.trim().isEmpty()) {
            throw new RuntimeException("El responsable no puede estar vacío");
        }
        return ticketRepository.findByResponsable(responsable).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorEstadoYPrioridad(String estado, String prioridad) {
        validarEstado(estado);
        validarPrioridad(prioridad);
        return ticketRepository.findByEstadoAndPrioridad(estado, prioridad).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TicketResponseDTO> buscarPorResponsableYEstado(String responsable, String estado) {
        if (responsable == null || responsable.trim().isEmpty()) {
            throw new RuntimeException("El responsable no puede estar vacío");
        }
        validarEstado(estado);
        return ticketRepository.findByResponsableAndEstado(responsable, estado).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
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
        if (prioridad == null || prioridad.trim().isEmpty()) {
            throw new RuntimeException("La prioridad no puede estar vacía");
        }
        String[] prioridadesValidas = {"BAJA", "MEDIA", "ALTA", "URGENTE"};
        boolean valida = false;
        for (String p : prioridadesValidas) {
            if (p.equals(prioridad.toUpperCase())) {
                valida = true;
                break;
            }
        }
        if (!valida) {
            throw new RuntimeException("Prioridad inválida. Debe ser: BAJA, MEDIA, ALTA o URGENTE");
        }
    }

    private void validarEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new RuntimeException("El estado no puede estar vacío");
        }
        String[] estadosValidos = {"ABIERTO", "EN_PROCESO", "PENDIENTE", "RESUELTO", "CERRADO"};
        boolean valido = false;
        for (String e : estadosValidos) {
            if (e.equals(estado.toUpperCase())) {
                valido = true;
                break;
            }
        }
        if (!valido) {
            throw new RuntimeException("Estado inválido. Debe ser: ABIERTO, EN_PROCESO, PENDIENTE, RESUELTO o CERRADO");
        }
    }
}
