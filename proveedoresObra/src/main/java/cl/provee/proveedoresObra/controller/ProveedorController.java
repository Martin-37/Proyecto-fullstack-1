package cl.provee.proveedoresObra.controller;

import cl.provee.proveedoresObra.dto.ProveedorRequestDTO;
import cl.provee.proveedoresObra.dto.ProveedorResponseDTO;
import cl.provee.proveedoresObra.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(proveedorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(
            @Valid @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.status(201).body(proveedorService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorRequestDTO dto) {
        return proveedorService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (proveedorService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/buscar")
    public ResponseEntity<List<ProveedorResponseDTO>> buscarPorNombre(
            @RequestParam String nombre) {
        return ResponseEntity.ok(proveedorService.buscarPorNombre(nombre));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProveedorResponseDTO>> buscarPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(proveedorService.buscarPorEstado(estado));
    }

    @GetMapping("/contacto")
    public ResponseEntity<List<ProveedorResponseDTO>> buscarPorContacto(
            @RequestParam String contacto) {
        return ResponseEntity.ok(proveedorService.buscarPorContacto(contacto));
    }

    @GetMapping("/telefono")
    public ResponseEntity<List<ProveedorResponseDTO>> buscarPorTelefono(
            @RequestParam String telefono) {
        return ResponseEntity.ok(proveedorService.buscarPorTelefono(telefono));
    }

    @GetMapping("/direccion")
    public ResponseEntity<List<ProveedorResponseDTO>> buscarPorDireccion(
            @RequestParam String direccion) {
        return ResponseEntity.ok(proveedorService.buscarPorDireccion(direccion));
    }

    @GetMapping("/global")
    public ResponseEntity<List<ProveedorResponseDTO>> busquedaGlobal(
            @RequestParam String texto) {
        return ResponseEntity.ok(proveedorService.busquedaGlobal(texto));
    }
}
