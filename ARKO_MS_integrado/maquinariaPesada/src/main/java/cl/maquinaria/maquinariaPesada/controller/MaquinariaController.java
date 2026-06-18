package cl.maquinaria.maquinariaPesada.controller;

import cl.maquinaria.maquinariaPesada.dto.MaquinariaRequestDTO;
import cl.maquinaria.maquinariaPesada.dto.MaquinariaResponseDTO;
import cl.maquinaria.maquinariaPesada.service.MaquinariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Maquinaria", description = "Gestión y seguimiento de maquinaria pesada")
public class MaquinariaController {

    private final MaquinariaService maquinariaService;

    // GET /api/maquinaria → 200 OK con lista
    @Operation(summary = "Obtener todas las maquinarias", description = "Retorna la lista completa de maquinarias registradas.")
    @GetMapping
    public ResponseEntity<List<MaquinariaResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(maquinariaService.obtenerTodos());
    }

    // GET /api/maquinaria/{id} → 200 OK o 404 Not Found
    @Operation(summary = "Obtener maquinaria por ID", description = "Busca y retorna una maquinaria específica por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<MaquinariaResponseDTO> obtenerPorId(
            @Parameter(description = "ID de la maquinaria", example = "1")
            @PathVariable Long id) {
        return maquinariaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/maquinaria → 201 Created
    // @Valid dispara las validaciones del DTO
    // Si un campo falla → GlobalExceptionHandler → 400
    //   { "nombre": "El nombre no puede estar vacío" }
    @Operation(summary = "Crear nueva maquinaria", description = "Registra una nueva maquinaria en el sistema.")
    @PostMapping
    public ResponseEntity<MaquinariaResponseDTO> crear(
            @Valid @RequestBody MaquinariaRequestDTO dto) {
        return ResponseEntity.status(201).body(maquinariaService.guardar(dto));
    }

    // PUT /api/maquinaria/{id} → 200 OK o 404 Not Found
    // @Valid también aplica aquí: mismas validaciones que en POST
    @Operation(summary = "Actualizar maquinaria", description = "Actualiza los datos de una maquinaria existente.")
    @PutMapping("/{id}")
    public ResponseEntity<MaquinariaResponseDTO> actualizar(
            @Parameter(description = "ID de la maquinaria a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody MaquinariaRequestDTO dto) {
        return maquinariaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/maquinaria/{id} → 204 No Content o 404 Not Found
    @Operation(summary = "Eliminar maquinaria", description = "Elimina una maquinaria del sistema por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la maquinaria a eliminar", example = "1")
            @PathVariable Long id) {
        if (maquinariaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        maquinariaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ── BÚSQUEDAS ────────────────────────────────────

    // GET /api/maquinaria/buscar?nombre=xxx
    @Operation(summary = "Buscar maquinaria por nombre", description = "Retorna maquinarias cuyo nombre coincida con el parámetro.")
    @GetMapping("/buscar")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorNombre(
            @Parameter(description = "Texto a buscar en el nombre", example = "excavadora")
            @RequestParam String nombre) {
        return ResponseEntity.ok(maquinariaService.buscarPorNombre(nombre));
    }

    // GET /api/maquinaria/estado/{estado}
    @Operation(summary = "Buscar maquinaria por estado", description = "Retorna maquinarias filtradas por su estado actual.")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorEstado(
            @Parameter(description = "Estado de la maquinaria", example = "Operativa")
            @PathVariable String estado) {
        return ResponseEntity.ok(maquinariaService.buscarPorEstado(estado));
    }

    // GET /api/maquinaria/responsable?responsable=xxx
    @Operation(summary = "Buscar maquinaria por responsable", description = "Retorna maquinarias asignadas a un responsable específico.")
    @GetMapping("/responsable")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorResponsable(
            @Parameter(description = "Nombre del responsable", example = "Juan Perez")
            @RequestParam String responsable) {
        return ResponseEntity.ok(maquinariaService.buscarPorResponsable(responsable));
    }

    // GET /api/maquinaria/equipo?equipo=xxx
    @Operation(summary = "Buscar maquinaria por tipo de equipo", description = "Retorna maquinarias filtradas por tipo de equipo.")
    @GetMapping("/equipo")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorEquipo(
            @Parameter(description = "Tipo de equipo", example = "Excavadora")
            @RequestParam String equipo) {
        return ResponseEntity.ok(maquinariaService.buscarPorEquipo(equipo));
    }

    // GET /api/maquinaria/ubicacion?ubicacion=xxx
    @Operation(summary = "Buscar maquinaria por ubicación", description = "Retorna maquinarias filtradas por ubicación.")
    @GetMapping("/ubicacion")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarPorUbicacion(
            @Parameter(description = "Texto a buscar en la ubicación", example = "Valparaiso")
            @RequestParam String ubicacion) {
        return ResponseEntity.ok(maquinariaService.buscarPorUbicacion(ubicacion));
    }

    // GET /api/maquinaria/mantenimiento?hasta=2025-09-01
    @Operation(summary = "Buscar maquinaria con mantenimiento próximo", description = "Retorna maquinarias con fecha de mantenimiento hasta la indicada.")
    @GetMapping("/mantenimiento")
    public ResponseEntity<List<MaquinariaResponseDTO>> buscarConMantenimientoHasta(
            @Parameter(description = "Fecha límite en formato yyyy-MM-dd", example = "2026-09-01")
            @RequestParam String hasta) {
        return ResponseEntity.ok(maquinariaService.buscarConMantenimientoHasta(LocalDate.parse(hasta)));
    }

    // GET /api/maquinaria/global?texto=xxx
    @Operation(summary = "Búsqueda global de maquinaria", description = "Busca maquinarias por cualquier campo de texto.")
    @GetMapping("/global")
    public ResponseEntity<List<MaquinariaResponseDTO>> busquedaGlobal(
            @Parameter(description = "Texto a buscar en todos los campos", example = "caterpillar")
            @RequestParam String texto) {
        return ResponseEntity.ok(maquinariaService.busquedaGlobal(texto));
    }
}
