package cl.dsy1103.usuarios.controller;

import cl.dsy1103.usuarios.dto.RolRequestDTO;
import cl.dsy1103.usuarios.dto.RolResponseDTO;
import cl.dsy1103.usuarios.service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(rolService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> obtenerPorId(@PathVariable Long id) {
        return rolService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RolResponseDTO> crear(@Valid @RequestBody RolRequestDTO dto) {
        return ResponseEntity.status(201).body(rolService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RolRequestDTO dto) {
        return rolService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (rolService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RolResponseDTO>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(rolService.buscarPorEstado(estado));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<RolResponseDTO>> buscarPorDescripcion(@RequestParam String texto) {
        return ResponseEntity.ok(rolService.buscarPorDescripcion(texto));
    }

    @GetMapping("/global")
    public ResponseEntity<List<RolResponseDTO>> busquedaGlobal(@RequestParam String texto) {
        return ResponseEntity.ok(rolService.busquedaGlobal(texto));
    }
}
