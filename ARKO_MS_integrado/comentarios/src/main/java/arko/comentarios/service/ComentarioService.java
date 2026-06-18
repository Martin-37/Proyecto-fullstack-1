package arko.comentarios.service;

import arko.comentarios.dto.ComentarioDetalleDTO;
import arko.comentarios.dto.ComentarioRequestDTO;
import arko.comentarios.dto.ComentarioResponseDTO;
import arko.comentarios.model.ComentarioTicket;
import arko.comentarios.repository.ComentarioRepository;
import arko.comentarios.webclient.PersonalClient;
import arko.comentarios.webclient.TicketsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final TicketsClient ticketsClient;
    private final PersonalClient personalClient;

    public ComentarioResponseDTO crear(ComentarioRequestDTO request, String token) {
        Map<String, Object> ticket = ticketsClient.obtenerTicket(request.getTicketId(), token);
        if (ticket == null) {
            throw new RuntimeException("El ticket " + request.getTicketId() + " no existe");
        }

        Map<String, Object> autor = personalClient.obtenerTrabajador(request.getAutorRun(), token);
        if (autor == null) {
            throw new RuntimeException("El trabajador " + request.getAutorRun() + " no existe");
        }

        ComentarioTicket comentario = new ComentarioTicket();
        comentario.setTicketId(request.getTicketId());
        comentario.setAutorRun(request.getAutorRun());
        comentario.setMensaje(request.getMensaje());

        return mapToDTO(comentarioRepository.save(comentario));
    }

    public List<ComentarioResponseDTO> obtenerPorTicket(Long ticketId) {
        return comentarioRepository.findByTicketId(ticketId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ComentarioResponseDTO obtenerPorId(Long id) {
        return mapToDTO(buscar(id));
    }

    public void eliminar(Long id) {
        comentarioRepository.delete(buscar(id));
    }

    public ComentarioDetalleDTO obtenerDetalle(Long id, String token) {
        ComentarioTicket comentario = buscar(id);
        Map<String, Object> ticket = ticketsClient.obtenerTicketConDocumento(comentario.getTicketId(), token);
        Map<String, Object> autor = personalClient.obtenerTrabajador(comentario.getAutorRun(), token);
        return new ComentarioDetalleDTO(
                comentario.getId(),
                comentario.getTicketId(),
                comentario.getAutorRun(),
                comentario.getMensaje(),
                comentario.getFecha(),
                ticket,
                autor);
    }

    private ComentarioTicket buscar(Long id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con ID: " + id));
    }

    private ComentarioResponseDTO mapToDTO(ComentarioTicket comentario) {
        return new ComentarioResponseDTO(
                comentario.getId(),
                comentario.getTicketId(),
                comentario.getAutorRun(),
                comentario.getMensaje(),
                comentario.getFecha());
    }
}
