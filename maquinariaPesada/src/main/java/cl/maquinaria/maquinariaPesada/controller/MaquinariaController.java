package cl.maquinaria.maquinariaPesada.controller;

import cl.maquinaria.maquinariaPesada.dto.MaquinariaRequestDTO;
import cl.maquinaria.maquinariaPesada.dto.MaquinariaResponseDTO;
import cl.maquinaria.maquinariaPesada.service.MaquinariaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════
 * MaquinariaController.java
 * RESPONSABILIDADES:
 *   1. No importa la entidad Maquinaria en ningún lugar
 *   2. POST y PUT reciben @Valid MaquinariaRequestDTO
 *      Si la validación falla → GlobalExceptionHandler
 *      devuelve 400 con mapa { campo: mensaje }
 *   3. Todos los métodos devuelven MaquinariaResponseDTO
 * ═══════════════════════════════════════════════════
 */
@RestController
@RequestMapping("/api/maquinaria")
@RequiredArgsConstructor
public class MaquinariaController {

    private final MaquinariaService maquinariaService;

    // GET /api/maquinaria → 200 OK con lista
    @GetMapping
    public ResponseEntity<List<MaquinariaResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(maquinariaService.obtenerTodos());
    }

    // GET /api/maquinaria/{id} → 200 OK o 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<MaquinariaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return maquinariaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/maquinaria → 201 Created
    // @Valid dispara las validaciones del DTO
    // Si un campo falla → GlobalExceptionHandler → 400
    //   { "nombre": "El nombre no puede estar vacío" }
    @PostMapping
    public ResponseEntity<MaquinariaResponseDTO> crear(
            @Valid @RequestBody MaquinariaRequestDTO dto) {
        return ResponseEntity.status(201).body(maquinariaService.guardar(dto));
    }

    // PUT /api/maquinaria/{id} → 200 OK o 404 Not Found
    // @Valid también aplica aquí: mismas validaciones que en POST
    @PutMapping("/{id}")
    public ResponseEntity<MaquinariaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MaquinariaRequestDTO dto) {
        return maquinariaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/maquinaria/{id} → 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (maquinariaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        maquinariaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ── BÚSQUEDAS ────────────────────────────────────

    // GET /api/maquinaria/buscar?nombre=xxx
    @GetMapping("/buscar")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorNombre(
            @RequestParam String nombre) {
        return ResponseEntity.ok(maquinariaService.buscarPorNombre(nombre));
    }

    // GET /api/maquinaria/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(maquinariaService.buscarPorEstado(estado));
    }

    // GET /api/maquinaria/responsable?responsable=xxx
    @GetMapping("/responsable")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorResponsable(
            @RequestParam String responsable) {
        return ResponseEntity.ok(maquinariaService.buscarPorResponsable(responsable));
    }

    // GET /api/maquinaria/equipo?equipo=xxx
    @GetMapping("/equipo")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorEquipo(
            @RequestParam String equipo) {
        return ResponseEntity.ok(maquinariaService.buscarPorEquipo(equipo));
    }

    // GET /api/maquinaria/ubicacion?ubicacion=xxx
    @GetMapping("/ubicacion")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorUbicacion(
            @RequestParam String ubicacion) {
        return ResponseEntity.ok(maquinariaService.buscarPorUbicacion(ubicacion));
    }

    // GET /api/maquinaria/mantenimiento?hasta=2025-09-01
    @GetMapping("/mantenimiento")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarConMantenimientoHasta(
            @RequestParam String hasta) {
        return ResponseEntity.ok(maquinariaService.buscarConMantenimientoHasta(LocalDate.parse(hasta)));
    }

    // GET /api/maquinaria/global?texto=xxx
    @GetMapping("/global")
    public ResponseEntity<List<MaquinariaResponseDTO>> busquedaGlobal(
            @RequestParam String texto) {
        return ResponseEntity.ok(maquinariaService.busquedaGlobal(texto));
    }
}
