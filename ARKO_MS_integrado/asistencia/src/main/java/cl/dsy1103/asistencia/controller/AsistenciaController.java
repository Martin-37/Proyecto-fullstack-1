package cl.dsy1103.asistencia.controller;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.dsy1103.asistencia.service.AsistenciaService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/asistencia")
@Tag(name = "Asistencia", description = "Marcaje de entrada/salida y cálculo de horas trabajadas")
public class AsistenciaController {


    @Autowired
    private AsistenciaService asistenciaService;

    @Operation(summary = "Marcar entrada", description = "Registra la hora de entrada de un empleado para una fecha determinada.")
    @DateTimeFormat
    @PostMapping("/marcarEntrada/{numrun_emp}/{fecha}")
    public ResponseEntity<?> marcarEntrada(
            @Parameter(description = "RUT (numrun) del empleado", example = "12345678")
            @PathVariable Integer numrun_emp,
            @Parameter(description = "Fecha en formato dd-MM-yyyy", example = "17-06-2026")
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha){
        return ResponseEntity.ok(asistenciaService.marcarEntrada(numrun_emp, fecha));
    }
    

    @Operation(summary = "Marcar salida", description = "Registra la hora de salida de un empleado para una fecha determinada.")
    @PutMapping("/marcarSalida/{numrun_emp}/{fecha}")
    public ResponseEntity<?> marcarSalida(
            @Parameter(description = "RUT (numrun) del empleado", example = "12345678")
            @PathVariable Integer numrun_emp,
            @Parameter(description = "Fecha en formato dd-MM-yyyy", example = "17-06-2026")
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha){
        return ResponseEntity.ok(asistenciaService.marcarSalida(numrun_emp, fecha));
    }

    @Operation(summary = "Calcular horas trabajadas", description = "Calcula las horas trabajadas por un empleado en una fecha específica.")
    @GetMapping("/calcularHorasTrabajadas/{numrun_emp}/{fecha}")
    public ResponseEntity<?> calcularHorasTrabajadas(
            @Parameter(description = "RUT (numrun) del empleado", example = "12345678")
            @PathVariable Integer numrun_emp,
            @Parameter(description = "Fecha en formato dd-MM-yyyy", example = "17-06-2026")
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha){
        return ResponseEntity.ok(asistenciaService.calcularHorasTrabajadas(numrun_emp, fecha));
    }

    @Operation(summary = "Calcular horas totales trabajadas", description = "Calcula las horas totales trabajadas por un empleado entre dos fechas.")
    @GetMapping("/calcularHorasTotalesTrabajadas/{numrun_emp}/{fecha1}/{fecha2}")
    public ResponseEntity<?> calcularHorasTotalesTrabajadas(
            @Parameter(description = "RUT (numrun) del empleado", example = "12345678")
            @PathVariable Integer numrun_emp,
            @Parameter(description = "Fecha de inicio en formato dd-MM-yyyy", example = "01-06-2026")
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha1,
            @Parameter(description = "Fecha de término en formato dd-MM-yyyy", example = "30-06-2026")
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy ") LocalDate fecha2){
        return ResponseEntity.ok(asistenciaService.calcularHorasTotalesTrabajadas(numrun_emp, fecha1, fecha2));
    } 

}
