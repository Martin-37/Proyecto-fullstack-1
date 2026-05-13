package cl.dsy1103.microservicios.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.dsy1103.microservicios.dto.ObraRequestDTO;
import cl.dsy1103.microservicios.dto.ObraResponseDTO;
import cl.dsy1103.microservicios.service.ObraService;

import java.util.List;

/**
 * ObraController.java
 * Controlador REST del microservicio de Obras.
 *   1. No importa la entidad Obra en ningun lugar.
 *   2. POST y PUT reciben @Valid ObraRequestDTO.
 *      Si la validacion falla -> GlobalExceptionHandler
 *      devuelve 400 con mapa { campo: mensaje }.
 *   3. Todos los metodos devuelven ObraResponseDTO.
 */
@RestController
@RequestMapping("/api/obras")
@RequiredArgsConstructor
public class ObraController {

    private final ObraService obraService;

    // GET /api/obras -> 200 OK con lista
    @GetMapping
    public ResponseEntity<List<ObraResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(obraService.obtenerTodas());
    }

    // GET /api/obras/{nombreObra} -> 200 OK o 404 Not Found
    @GetMapping("/{nombreObra}")
    public ResponseEntity<ObraResponseDTO> obtenerPorNombre(
            @PathVariable String nombreObra) {
        return obraService.obtenerPorNombre(nombreObra)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/obras -> 201 Created
    // @Valid dispara las validaciones del DTO.
    // Si un campo falla -> GlobalExceptionHandler -> 400
    //   { "nombreObra": "El nombre de la obra no puede estar vacio" }
    // Si el responsableNumrun no existe -> GlobalExceptionHandler -> 400
    //   { "error": "Trabajador no encontrado con numrun: 99" }
    @PostMapping
    public ResponseEntity<ObraResponseDTO> crear(
            @Valid @RequestBody ObraRequestDTO dto) {
        return ResponseEntity.status(201).body(obraService.guardar(dto));
    }

    // PUT /api/obras/{nombreObra} -> 200 OK o 404 Not Found
    // @Valid tambien aplica aqui: mismas validaciones que en POST.
    @PutMapping("/{nombreObra}")
    public ResponseEntity<ObraResponseDTO> actualizar(
            @PathVariable String nombreObra,
            @Valid @RequestBody ObraRequestDTO dto) {
        return obraService.actualizar(nombreObra, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/obras/{nombreObra} -> 204 No Content o 404 Not Found
    @DeleteMapping("/{nombreObra}")
    public ResponseEntity<Void> eliminar(@PathVariable String nombreObra) {
        if (obraService.obtenerPorNombre(nombreObra).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        obraService.eliminar(nombreObra);
        return ResponseEntity.noContent().build();
    }

    // ── BUSQUEDAS ──────────────────────────────────────────────────────────

    // GET /api/obras/buscar/estado?estado=En%20curso
    @GetMapping("/buscar/estado")
    public ResponseEntity<List<ObraResponseDTO>> buscarPorEstado(
            @RequestParam String estado) {
        return ResponseEntity.ok(obraService.buscarPorEstado(estado));
    }

    // GET /api/obras/buscar/ubicacion?ubicacion=Santiago
    @GetMapping("/buscar/ubicacion")
    public ResponseEntity<List<ObraResponseDTO>> buscarPorUbicacion(
            @RequestParam String ubicacion) {
        return ResponseEntity.ok(obraService.buscarPorUbicacion(ubicacion));
    }

    // GET /api/obras/responsable/{numrun}
    @GetMapping("/responsable/{numrun}")
    public ResponseEntity<List<ObraResponseDTO>> buscarPorResponsable(
            @PathVariable Long numrun) {
        return ResponseEntity.ok(obraService.buscarPorResponsable(numrun));
    }
}
