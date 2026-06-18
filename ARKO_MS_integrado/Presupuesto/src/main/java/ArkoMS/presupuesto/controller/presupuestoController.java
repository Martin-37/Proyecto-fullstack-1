package ArkoMS.presupuesto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ArkoMS.presupuesto.dto.*;
import ArkoMS.presupuesto.model.Presupuesto;
import ArkoMS.presupuesto.service.PresupuestoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/api/presupuestos")
@Tag(name = "Presupuesto", description = "Gestión de presupuestos de obras")
public class presupuestoController {
    private final PresupuestoService presupuestoService;

    @Operation(summary = "Obtener todos los presupuestos", description = "Retorna la lista completa de presupuestos registrados.")
    @GetMapping
    public ResponseEntity<?> obtenerPresupuestos() {
        return ResponseEntity.ok(presupuestoService.obtenerTodosLosPresupuestos());

    }

    @Operation(summary = "Obtener presupuesto por ID", description = "Busca y retorna un presupuesto específico por su ID.")
    @GetMapping("buscar/{id}")
    public ResponseEntity<?> obtenerPresupuestoPorId(
            @Parameter(description = "ID del presupuesto", example = "1")
            @PathVariable Long id) {
        return presupuestoService.obtenerPresupuestoPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cambiar estado del presupuesto", description = "Actualiza el estado de un presupuesto existente.")
    @PutMapping("cambiarEstado/{id}/estado")
    public ResponseEntity<?> cambiarEstadoPresupuesto(
            @Parameter(description = "ID del presupuesto", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado del presupuesto", example = "APROBADO")
            @RequestBody String nuevoEstado) {
        return ResponseEntity.ok(presupuestoService.CambiarEstadoPresupuesto(id, nuevoEstado));
    }

    @Operation(summary = "Crear presupuesto", description = "Registra un nuevo presupuesto.")
    @PostMapping("crear")
    public ResponseEntity<?> crearPresupuesto(@Valid @RequestBody PresupuestoRequestDTO presupuesto) {
        Presupuesto nuevoPresupuesto = presupuestoService.crearPresupuesto(presupuesto);
        return ResponseEntity.status(201).body(nuevoPresupuesto);
    }
    
    @Operation(summary = "Eliminar presupuesto", description = "Elimina un presupuesto por su ID.")
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarPresupuesto(
            @Parameter(description = "ID del presupuesto a eliminar", example = "1")
            @PathVariable Long id) {
        presupuestoService.eliminarPresupuesto(id);
        return ResponseEntity.ok().build();
    }
    
}
