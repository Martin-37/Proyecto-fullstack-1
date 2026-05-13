package arko.tickets.controller;

import arko.tickets.dto.TicketRequestDTO;
import arko.tickets.dto.TicketResponseDTO;
import arko.tickets.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> obtenerTodos() {
        List<TicketResponseDTO> tickets = ticketService.obtenerTodos();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> obtenerPorId(@PathVariable Long id) {
        TicketResponseDTO ticket = ticketService.obtenerPorId(id);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> crear(@Valid @RequestBody TicketRequestDTO requestDTO) {
        TicketResponseDTO nuevoTicket = ticketService.guardar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTicket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequestDTO requestDTO) {
        TicketResponseDTO actualizado = ticketService.actualizar(id, requestDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ticketService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorTitulo(@RequestParam String titulo) {
        List<TicketResponseDTO> tickets = ticketService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/buscar/estado/{estado}")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorEstado(@PathVariable String estado) {
        List<TicketResponseDTO> tickets = ticketService.buscarPorEstado(estado);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/buscar/prioridad/{prioridad}")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorPrioridad(@PathVariable String prioridad) {
        List<TicketResponseDTO> tickets = ticketService.buscarPorPrioridad(prioridad);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/buscar/responsable/{responsable}")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorResponsable(@PathVariable String responsable) {
        List<TicketResponseDTO> tickets = ticketService.buscarPorResponsable(responsable);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/buscar/descripcion")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorDescripcion(@RequestParam String descripcion) {
        List<TicketResponseDTO> tickets = ticketService.buscarPorDescripcion(descripcion);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/filtro/estado-prioridad")
    public ResponseEntity<List<TicketResponseDTO>> filtrarPorEstadoYPrioridad(
            @RequestParam String estado,
            @RequestParam String prioridad) {
        List<TicketResponseDTO> tickets = ticketService.buscarPorEstadoYPrioridad(estado, prioridad);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/filtro/responsable-estado")
    public ResponseEntity<List<TicketResponseDTO>> filtrarPorResponsableYEstado(
            @RequestParam String responsable,
            @RequestParam String estado) {
        List<TicketResponseDTO> tickets = ticketService.buscarPorResponsableYEstado(responsable, estado);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/estadisticas/contar-estado/{estado}")
    public ResponseEntity<Long> contarPorEstado(@PathVariable String estado) {
        Long cantidad = ticketService.contarPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }

    @GetMapping("/estadisticas/contar-prioridad/{prioridad}")
    public ResponseEntity<Long> contarPorPrioridad(@PathVariable String prioridad) {
        Long cantidad = ticketService.contarPorPrioridad(prioridad);
        return ResponseEntity.ok(cantidad);
    }
}
