package ArkoMS.presupuesto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ArkoMS.presupuesto.model.Finanza;
import ArkoMS.presupuesto.dto.FinanzaRequestDTO;
import ArkoMS.presupuesto.service.FinanzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/api/finanzas")
@Tag(name = "Finanzas", description = "Gestión de gastos e ingresos financieros")
public class FinanzaController {
    private final FinanzaService presupuestoService;

    // Formato yyyy-MM para agrupar los gastos por mes en el endpoint de resumen
    private static final DateTimeFormatter FMT_MES = DateTimeFormatter.ofPattern("yyyy-MM");

    @Operation(summary = "Resumen de gastos mensuales", description = "Suma los gastos agrupados por mes entre dos fechas (formato yyyy-MM-dd).")
    @GetMapping("gastos-mensuales")
    public ResponseEntity<?> gastosMensuales(
            @Parameter(description = "Fecha de inicio, formato yyyy-MM-dd", example = "2026-01-01")
            @RequestParam String inicio,
            @Parameter(description = "Fecha de término, formato yyyy-MM-dd", example = "2026-06-30")
            @RequestParam String fin) {
        try {
            // LocalDate.parse espera el formato ISO yyyy-MM-dd por defecto,
            // sin componente de hora ni zona horaria: evita el bug de
            // comparaciones que fallaban con java.util.Date + milisegundos.
            LocalDate dInicio = LocalDate.parse(inicio);
            LocalDate dFin = LocalDate.parse(fin);
            List<Finanza> finanzas = presupuestoService.obtenerFinanzasEntreFechas(dInicio, dFin);

            Map<String, Integer> resultado = new LinkedHashMap<>();
            for (Finanza f : finanzas) {
                String mes = f.getFecha().format(FMT_MES);
                Integer g = f.getGastos() == null ? 0 : f.getGastos();
                resultado.put(mes, resultado.getOrDefault(mes, 0) + g);
            }

            return ResponseEntity.ok(resultado);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Formato de fecha inválido. Use yyyy-MM-dd");
        }
    }

    @Operation(summary = "Buscar finanzas por fecha exacta", description = "Retorna los registros financieros que coinciden con la fecha indicada (formato yyyy-MM-dd).")
    @GetMapping("por-fecha")
    public ResponseEntity<?> obtenerPorFecha(
            @Parameter(description = "Fecha exacta, formato yyyy-MM-dd", example = "2026-06-18")
            @RequestParam String fecha) {
        try {
            LocalDate d = LocalDate.parse(fecha);
            List<Finanza> finanzas = presupuestoService.obtenerFinanzasPorFecha(d);
            return ResponseEntity.ok(finanzas);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Formato de fecha inválido. Use yyyy-MM-dd");
        }
    }

    @Operation(summary = "Obtener todas las finanzas", description = "Retorna la lista completa de registros financieros.")
    @GetMapping("todos")
    public ResponseEntity<?> obtenerTodas() {
        List<Finanza> finanzas = presupuestoService.obtenerTodasFinanzas();
        return ResponseEntity.ok(finanzas);
    }

    @Operation(summary = "Crear registro financiero", description = "Registra un nuevo movimiento de gasto/ingreso.")
    @PostMapping
    public ResponseEntity<?> crearFinanza(@Valid @RequestBody FinanzaRequestDTO dto) {
        Finanza finanza = presupuestoService.crearFinanza(dto);
        return ResponseEntity.status(201).body(finanza);
    }

    @Operation(summary = "Actualizar registro financiero", description = "Actualiza un movimiento financiero existente por su ID.")
    @PutMapping("{id}")
    public ResponseEntity<?> actualizarFinanza(
            @Parameter(description = "ID del registro financiero", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody FinanzaRequestDTO dto) {
        Finanza finanza = presupuestoService.actualizarFinanza(id, dto);
        return ResponseEntity.ok(finanza);
    }

    @Operation(summary = "Eliminar registro financiero", description = "Elimina un movimiento financiero por su ID.")
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarFinanza(
            @Parameter(description = "ID del registro financiero", example = "1")
            @PathVariable Long id) {
        boolean eliminado = presupuestoService.eliminarFinanza(id);
        if (!eliminado) {
            return ResponseEntity.status(404).body("Finanza no encontrada con ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }

}