package arko.cl.personal.controller;

import arko.cl.personal.dto.TrabajadorRequestDTO;
import arko.cl.personal.dto.TrabajadorResponseDTO;
import arko.cl.personal.service.TrabajadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    public TrabajadorController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    @PostMapping
    public ResponseEntity<TrabajadorResponseDTO> crear(@Valid @RequestBody TrabajadorRequestDTO dto) {
        TrabajadorResponseDTO respuesta = trabajadorService.crearTrabajador(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping("/{numrunTrab}")
    public ResponseEntity<TrabajadorResponseDTO> obtener(@PathVariable Long numrunTrab) {
        TrabajadorResponseDTO respuesta = trabajadorService.obtenerTrabajador(numrunTrab);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<TrabajadorResponseDTO>> obtenerTodos() {
        List<TrabajadorResponseDTO> respuesta = trabajadorService.obtenerTodos();
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{numrunTrab}")
    public ResponseEntity<TrabajadorResponseDTO> actualizar(@PathVariable Long numrunTrab, @Valid @RequestBody TrabajadorRequestDTO dto) {
        TrabajadorResponseDTO respuesta = trabajadorService.actualizarTrabajador(numrunTrab, dto);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{numrunTrab}")
    public ResponseEntity<Void> eliminar(@PathVariable Long numrunTrab) {
        trabajadorService.eliminarTrabajador(numrunTrab);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/cargo")
    public ResponseEntity<List<TrabajadorResponseDTO>> buscarPorCargo(@RequestParam String cargo) {
        List<TrabajadorResponseDTO> respuesta = trabajadorService.buscarPorCargo(cargo);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscar/estado")
    public ResponseEntity<List<TrabajadorResponseDTO>> buscarPorEstado(@RequestParam String estado) {
        List<TrabajadorResponseDTO> respuesta = trabajadorService.buscarPorEstado(estado);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<TrabajadorResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<TrabajadorResponseDTO> respuesta = trabajadorService.buscarPorNombre(nombre);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/buscar/filtro")
    public ResponseEntity<List<TrabajadorResponseDTO>> buscarPorCargoYEstado(@RequestParam String cargo, @RequestParam String estado) {
        List<TrabajadorResponseDTO> respuesta = trabajadorService.buscarPorCargoYEstado(cargo, estado);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/estadisticas/cargo/{cargo}")
    public ResponseEntity<Long> contarPorCargo(@PathVariable String cargo) {
        Long cantidad = trabajadorService.contarPorCargo(cargo);
        return ResponseEntity.ok(cantidad);
    }

    @GetMapping("/estadisticas/estado/{estado}")
    public ResponseEntity<Long> contarPorEstado(@PathVariable String estado) {
        Long cantidad = trabajadorService.contarPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }
}
