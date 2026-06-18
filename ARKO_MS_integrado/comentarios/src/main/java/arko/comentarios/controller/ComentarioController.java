package arko.comentarios.controller;

import arko.comentarios.dto.ComentarioDetalleDTO;
import arko.comentarios.dto.ComentarioRequestDTO;
import arko.comentarios.dto.ComentarioResponseDTO;
import arko.comentarios.service.ComentarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> crear(
            @Valid @RequestBody ComentarioRequestDTO request,
            @RequestHeader("Authorization") String token) {
        ComentarioResponseDTO creado = comentarioService.crear(request, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<ComentarioResponseDTO>> obtenerPorTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(comentarioService.obtenerPorTicket(ticketId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        comentarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<ComentarioDetalleDTO> obtenerDetalle(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(comentarioService.obtenerDetalle(id, token));
    }
}
