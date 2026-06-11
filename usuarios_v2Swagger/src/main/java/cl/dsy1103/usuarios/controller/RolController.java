package cl.dsy1103.usuarios.controller;

import cl.dsy1103.usuarios.dto.RolRequestDTO;
import cl.dsy1103.usuarios.dto.RolResponseDTO;
import cl.dsy1103.usuarios.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Gestión de roles del sistema")
@SecurityRequirement(name = "bearerAuth")
public class RolController {

    private final RolService rolService;

    @Operation(summary = "Obtener todos los roles", description = "Retorna la lista completa de roles registrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(rolService.obtenerTodos());
    }

    @Operation(summary = "Obtener rol por ID", description = "Busca y retorna un rol específico por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol encontrado",
            content = @Content(schema = @Schema(implementation = RolResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> obtenerPorId(
            @Parameter(description = "ID del rol", required = true, example = "1")
            @PathVariable Long id) {
        return rolService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo rol", description = "Registra un nuevo rol en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Rol creado exitosamente",
            content = @Content(schema = @Schema(implementation = RolResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RolResponseDTO> crear(@Valid @RequestBody RolRequestDTO dto) {
        return ResponseEntity.status(201).body(rolService.guardar(dto));
    }

    @Operation(summary = "Actualizar rol", description = "Actualiza los datos de un rol existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = RolResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> actualizar(
            @Parameter(description = "ID del rol a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody RolRequestDTO dto) {
        return rolService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar rol", description = "Elimina un rol del sistema por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Rol eliminado exitosamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del rol a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        if (rolService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar roles por estado", description = "Retorna roles filtrados por estado (ej: ACTIVO, INACTIVO).")
    @ApiResponse(responseCode = "200", description = "Lista de roles con ese estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RolResponseDTO>> buscarPorEstado(
            @Parameter(description = "Estado del rol", required = true, example = "ACTIVO")
            @PathVariable String estado) {
        return ResponseEntity.ok(rolService.buscarPorEstado(estado));
    }

    @Operation(summary = "Buscar roles por descripción", description = "Retorna roles cuya descripción coincida con el texto.")
    @ApiResponse(responseCode = "200", description = "Resultados de búsqueda")
    @GetMapping("/buscar")
    public ResponseEntity<List<RolResponseDTO>> buscarPorDescripcion(
            @Parameter(description = "Texto a buscar en la descripción", required = true, example = "admin")
            @RequestParam String texto) {
        return ResponseEntity.ok(rolService.buscarPorDescripcion(texto));
    }

    @Operation(summary = "Búsqueda global de roles", description = "Busca roles por cualquier campo de texto.")
    @ApiResponse(responseCode = "200", description = "Resultados de búsqueda global")
    @GetMapping("/global")
    public ResponseEntity<List<RolResponseDTO>> busquedaGlobal(
            @Parameter(description = "Texto a buscar en todos los campos", required = true, example = "gestor")
            @RequestParam String texto) {
        return ResponseEntity.ok(rolService.busquedaGlobal(texto));
    }
}
