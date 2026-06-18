package arko.cl.documentos.controller;

import arko.cl.documentos.dto.DocumentoRequestDTO;
import arko.cl.documentos.dto.DocumentoResponseDTO;
import arko.cl.documentos.model.Documento.EstadoDocumento;
import arko.cl.documentos.service.DocumentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;

    // CRUD
    @PostMapping
    public ResponseEntity<DocumentoResponseDTO> crearDocumento(@Valid @RequestBody DocumentoRequestDTO request) {
        DocumentoResponseDTO documento = documentoService.crearDocumento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(documento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoResponseDTO> obtenerDocumento(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.obtenerDocumento(id));
    }

    @GetMapping
    public ResponseEntity<List<DocumentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(documentoService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoResponseDTO> actualizarDocumento(
            @PathVariable Long id,
            @Valid @RequestBody DocumentoRequestDTO request) {
        DocumentoResponseDTO documento = documentoService.actualizarDocumento(id, request);
        return ResponseEntity.ok(documento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDocumento(@PathVariable Long id) {
        documentoService.eliminarDocumento(id);
        return ResponseEntity.noContent().build();
    }

    // Búsquedas
    @GetMapping("/obra/{obraAsociada}")
    public ResponseEntity<List<DocumentoResponseDTO>> documentosPorObra(@PathVariable Long obraAsociada) {
        return ResponseEntity.ok(documentoService.documentosPorObra(obraAsociada));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<DocumentoResponseDTO>> documentosPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(documentoService.documentosPorTipo(tipo));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DocumentoResponseDTO>> documentosPorEstado(@PathVariable EstadoDocumento estado) {
        return ResponseEntity.ok(documentoService.documentosPorEstado(estado));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DocumentoResponseDTO>> buscarPorNombre(@RequestParam String nombrePlano) {
        return ResponseEntity.ok(documentoService.buscarPorNombre(nombrePlano));
    }

    // Estadísticas
    @GetMapping("/contar/obra/{obraAsociada}")
    public ResponseEntity<Long> contarPorObra(@PathVariable Long obraAsociada) {
        long total = documentoService.contarPorObra(obraAsociada);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/contar/estado/{estado}")
    public ResponseEntity<Long> contarPorEstado(@PathVariable EstadoDocumento estado) {
        long total = documentoService.contarPorEstado(estado);
        return ResponseEntity.ok(total);
    }
}
