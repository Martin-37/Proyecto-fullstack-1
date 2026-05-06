package arko.tickets.controller;

import arko.tickets.dto.TicketRequestDTO;
import arko.tickets.dto.TicketResponseDTO;
import arko.tickets.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(ticketService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ticketService.obtenerPorId(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> crear(@Valid @RequestBody TicketRequestDTO dto) {
        return ResponseEntity.status(201).body(ticketService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> actualizar(@PathVariable Long id,
            @Valid @RequestBody TicketRequestDTO dto) {
        return ticketService.actualizar(id, dto).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ticketService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ticketService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(ticketService.buscarPorTitulo(titulo));
    }

    @GetMapping("/buscar/estado/{estado}")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(ticketService.buscarPorEstado(estado));
    }

    @GetMapping("/buscar/prioridad/{prioridad}")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorPrioridad(@PathVariable String prioridad) {
        return ResponseEntity.ok(ticketService.buscarPorPrioridad(prioridad));
    }

    @GetMapping("/buscar/responsable/{responsable}")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorResponsable(@PathVariable String responsable) {
        return ResponseEntity.ok(ticketService.buscarPorResponsable(responsable));
    }

    @GetMapping("/filtro/estado-prioridad")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorEstadoYPrioridad(
            @RequestParam String estado, @RequestParam String prioridad) {
        return ResponseEntity.ok(ticketService.buscarPorEstadoYPrioridad(estado, prioridad));
    }

    @GetMapping("/filtro/responsable-estado")
    public ResponseEntity<List<TicketResponseDTO>> buscarPorResponsableYEstado(
            @RequestParam String responsable, @RequestParam String estado) {
        return ResponseEntity.ok(ticketService.buscarPorResponsableYEstado(responsable, estado));
    }

    @GetMapping("/estadisticas/contar-estado/{estado}")
    public ResponseEntity<Long> contarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(ticketService.contarPorEstado(estado));
    }

    @GetMapping("/estadisticas/contar-prioridad/{prioridad}")
    public ResponseEntity<Long> contarPorPrioridad(@PathVariable String prioridad) {
        return ResponseEntity.ok(ticketService.contarPorPrioridad(prioridad));
    }
}
