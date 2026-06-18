package cl.dsy1103.microservicios.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Obras", description = "Gestión de obras de construcción")
public class ObraController {

    private final ObraService obraService;

    // GET /api/obras -> 200 OK con lista
    @Operation(summary = "Obtener todas las obras", description = "Retorna la lista completa de obras registradas.")
    @GetMapping
    public ResponseEntity<List<ObraResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(obraService.obtenerTodas());
    }

    // GET /api/obras/{nombreObra} -> 200 OK o 404 Not Found
    @Operation(summary = "Obtener obra por nombre", description = "Busca una obra específica por su nombre (PK).")
    @GetMapping("/{nombreObra}")
    public ResponseEntity<ObraResponseDTO> obtenerPorNombre(
            @Parameter(description = "Nombre de la obra", example = "Edificio Central Valparaiso")
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
    @Operation(summary = "Crear nueva obra", description = "Registra una nueva obra. El responsable debe existir previamente como trabajador.")
    @PostMapping
    public ResponseEntity<ObraResponseDTO> crear(
            @Valid @RequestBody ObraRequestDTO dto) {
        return ResponseEntity.status(201).body(obraService.guardar(dto));
    }

    // PUT /api/obras/{nombreObra} -> 200 OK o 404 Not Found
    // @Valid tambien aplica aqui: mismas validaciones que en POST.
    @Operation(summary = "Actualizar obra", description = "Actualiza los datos de una obra existente.")
    @PutMapping("/{nombreObra}")
    public ResponseEntity<ObraResponseDTO> actualizar(
            @Parameter(description = "Nombre de la obra a actualizar")
            @PathVariable String nombreObra,
            @Valid @RequestBody ObraRequestDTO dto) {
        return obraService.actualizar(nombreObra, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/obras/{nombreObra} -> 204 No Content o 404 Not Found
    @Operation(summary = "Eliminar obra", description = "Elimina una obra del sistema por su nombre.")
    @DeleteMapping("/{nombreObra}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Nombre de la obra a eliminar")
            @PathVariable String nombreObra) {
        if (obraService.obtenerPorNombre(nombreObra).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        obraService.eliminar(nombreObra);
        return ResponseEntity.noContent().build();
    }

    // ── BUSQUEDAS ──────────────────────────────────────────────────────────

    // GET /api/obras/buscar/estado?estado=En%20curso
    @Operation(summary = "Buscar obras por estado", description = "Retorna obras filtradas por su estado actual.")
    @GetMapping("/buscar/estado")
    public ResponseEntity<List<ObraResponseDTO>> buscarPorEstado(
            @Parameter(description = "Estado de la obra", example = "En ejecucion")
            @RequestParam String estado) {
        return ResponseEntity.ok(obraService.buscarPorEstado(estado));
    }

    // GET /api/obras/buscar/ubicacion?ubicacion=Santiago
    @Operation(summary = "Buscar obras por ubicación", description = "Retorna obras cuya ubicación contenga el texto indicado.")
    @GetMapping("/buscar/ubicacion")
    public ResponseEntity<List<ObraResponseDTO>> buscarPorUbicacion(
            @Parameter(description = "Texto a buscar en la ubicación", example = "Valparaiso")
            @RequestParam String ubicacion) {
        return ResponseEntity.ok(obraService.buscarPorUbicacion(ubicacion));
    }

    // GET /api/obras/responsable/{numrun}
    @Operation(summary = "Buscar obras por responsable", description = "Retorna todas las obras a cargo de un trabajador específico.")
    @GetMapping("/responsable/{numrun}")
    public ResponseEntity<List<ObraResponseDTO>> buscarPorResponsable(
            @Parameter(description = "RUT (numrun) del responsable", example = "12345678")
            @PathVariable Long numrun) {
        return ResponseEntity.ok(obraService.buscarPorResponsable(numrun));
    }
}
