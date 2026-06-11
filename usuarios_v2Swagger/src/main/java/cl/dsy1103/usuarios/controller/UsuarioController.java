package cl.dsy1103.usuarios.controller;

import cl.dsy1103.usuarios.dto.LoginRequestDTO;
import cl.dsy1103.usuarios.dto.LoginResponseDTO;
import cl.dsy1103.usuarios.dto.UsuarioRequestDTO;
import cl.dsy1103.usuarios.dto.UsuarioResponseDTO;
import cl.dsy1103.usuarios.service.UsuarioAuthService;
import cl.dsy1103.usuarios.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioAuthService usuarioAuthService;

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica al usuario y retorna un token JWT. Este endpoint es público, no requiere autenticación.",
        security = {}
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso, retorna token JWT",
            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas", content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(usuarioAuthService.login(request));
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista completa de usuarios registrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT requerido", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @Operation(summary = "Obtener usuario por ID", description = "Busca y retorna un usuario específico por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo usuario en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(201).body(usuarioService.guardar(dto));
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        if (usuarioService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar usuarios por nombre", description = "Retorna usuarios cuyo nombre coincida con el parámetro.")
    @ApiResponse(responseCode = "200", description = "Resultados de búsqueda")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNombre(
            @Parameter(description = "Texto a buscar en el nombre de usuario", required = true, example = "admin")
            @RequestParam String nombre) {
        return ResponseEntity.ok(usuarioService.buscarPorNombre(nombre));
    }

    @Operation(summary = "Buscar usuarios por rol", description = "Retorna todos los usuarios asociados a un rol específico.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios con ese rol")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorRol(
            @Parameter(description = "ID del rol", required = true, example = "1")
            @PathVariable Long rolId) {
        return ResponseEntity.ok(usuarioService.buscarPorRol(rolId));
    }

    @Operation(summary = "Buscar usuarios por estado", description = "Retorna usuarios filtrados por estado (ej: ACTIVO, INACTIVO).")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios con ese estado")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorEstado(
            @Parameter(description = "Estado del usuario", required = true, example = "ACTIVO")
            @PathVariable String estado) {
        return ResponseEntity.ok(usuarioService.buscarPorEstado(estado));
    }

    @Operation(summary = "Buscar usuario por email", description = "Retorna el usuario que tenga el email especificado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "No existe usuario con ese email", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/email")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(
            @Parameter(description = "Email del usuario", required = true, example = "usuario@ejemplo.cl")
            @RequestParam String email) {
        return usuarioService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Búsqueda global de usuarios", description = "Busca usuarios por cualquier campo de texto (nombre, email, etc.).")
    @ApiResponse(responseCode = "200", description = "Resultados de búsqueda global")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/global")
    public ResponseEntity<List<UsuarioResponseDTO>> busquedaGlobal(
            @Parameter(description = "Texto a buscar en todos los campos", required = true, example = "juan")
            @RequestParam String texto) {
        return ResponseEntity.ok(usuarioService.busquedaGlobal(texto));
    }
}
