package cl.dsy1103.microservicios.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.dsy1103.microservicios.service.AsistenciaService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;





@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {


    @Autowired
    private AsistenciaService asistenciaService;

    @DateTimeFormat
    @PostMapping("/marcarEntrada/{numrun_emp}/{fecha}")
    public ResponseEntity<?> marcarEntrada(@PathVariable Integer numrun_emp, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha){
        return ResponseEntity.ok(asistenciaService.marcarEntrada(numrun_emp, fecha));
    }
    

    @PutMapping("/marcarSalida/{numrun_emp}/{fecha}")
    public ResponseEntity<?> marcarSalida(@PathVariable Integer numrun_emp, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha){
        return ResponseEntity.ok(asistenciaService.marcarSalida(numrun_emp, fecha));
    }

    @GetMapping("/calcularHorasTrabajadas/{numrun_emp}/{fecha}")
    public ResponseEntity<?> calcularHorasTrabajadas(@PathVariable Integer numrun_emp, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha){
        return ResponseEntity.ok(asistenciaService.calcularHorasTrabajadas(numrun_emp, fecha));
    }

    @GetMapping("/calcularHorasTotalesTrabajadas/{numrun_emp}/{fecha1}/{fecha2}")
    public ResponseEntity<?> calcularHorasTotalesTrabajadas(@PathVariable Integer numrun_emp, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fecha1, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy ") LocalDate fecha2){
        return ResponseEntity.ok(asistenciaService.calcularHorasTotalesTrabajadas(numrun_emp, fecha1, fecha2));
    } 

}
