package ArkoMS.presupuesto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ArkoMS.presupuesto.dto.*;
import ArkoMS.presupuesto.model.Presupuesto;
import ArkoMS.presupuesto.service.PresupuestoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/api/presupuestos")
public class presupuestoController {
    private final PresupuestoService presupuestoService;

    @GetMapping
    public ResponseEntity<?> obtenerPresupuestos() {
        return ResponseEntity.ok(presupuestoService.obtenerTodosLosPresupuestos());

    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> obtenerPresupuestoPorId(@PathVariable Long id) {
        return presupuestoService.obtenerPresupuestoPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("cambiarEstado/{id}/estado")
    public ResponseEntity<?> cambiarEstadoPresupuesto(@PathVariable Long id, @RequestBody String nuevoEstado) {
        return ResponseEntity.ok(presupuestoService.CambiarEstadoPresupuesto(id, nuevoEstado));
    }

    @PostMapping("crear")
    public ResponseEntity<?> crearPresupuesto(@Valid @RequestBody PresupuestoRequestDTO presupuesto) {
        Presupuesto nuevoPresupuesto = presupuestoService.crearPresupuesto(presupuesto);
        return ResponseEntity.status(201).body(nuevoPresupuesto);
    }
    
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarPresupuesto(@PathVariable Long id) {
        presupuestoService.eliminarPresupuesto(id);
        return ResponseEntity.ok().build();
    }
    
}
