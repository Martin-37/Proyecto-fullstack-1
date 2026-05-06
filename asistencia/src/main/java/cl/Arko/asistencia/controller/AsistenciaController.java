package cl.Arko.asistencia.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.Arko.asistencia.Service.AsistenciaService;
import cl.Arko.asistencia.dto.AsistenciaRequestDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {


    @Autowired
    private AsistenciaService asistenciaService;


    @PostMapping("/marcarEntrada/{numrun_emp}/{fecha}")
    public ResponseEntity<?> marcarEntrada(@PathVariable Integer numrun_emp, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") String fecha){
        return ResponseEntity.ok(asistenciaService.marcarEntrada(numrun_emp, Date.valueOf(fecha)));
    }
    

    @PutMapping("/marcarSalida/{numrun_emp}/{fecha}")
    public ResponseEntity<?> marcarSalida(@PathVariable Integer numrun_emp, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") String fecha){
        return ResponseEntity.ok(asistenciaService.marcarSalida(numrun_emp, Date.valueOf(fecha)));
    }

    @GetMapping("/calcularHorasTrabajadas/{numrun_emp}/{fecha}")
    public ResponseEntity<?> calcularHorasTrabajadas(@PathVariable Integer numrun_emp, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") String fecha){
        return ResponseEntity.ok(asistenciaService.calcularHorasTrabajadas(numrun_emp, Date.valueOf(fecha)));
    }

    @GetMapping("/calcularHorasTotalesTrabajadas/{numrun_emp}/{fecha1}/{fecha2}")
    public ResponseEntity<?> calcularHorasTotalesTrabajadas(@PathVariable Integer numrun_emp, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") String fecha1, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy ") String fecha2){
        return ResponseEntity.ok(asistenciaService.calcularHorasTotalesTrabajadas(numrun_emp, Date.valueOf(fecha1), Date.valueOf(fecha2)));
    } 

}

