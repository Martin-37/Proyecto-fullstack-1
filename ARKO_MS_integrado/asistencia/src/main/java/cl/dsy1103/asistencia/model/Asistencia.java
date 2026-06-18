package cl.dsy1103.asistencia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "Asistencia")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asistencia {

    @Id
    @Column(name = "Empleado", nullable = false)
    // Extrae el Rut del trabajador para identificarlo. No se usa ID generado.
    private Integer numrun_emp;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora_llegada;

    @Column
    private LocalTime hora_salida;

    //Almacena si se marco la salida para evitar errores, False = no se a marcado True = ya marco salida
    @Column(nullable = false)
    private boolean estado;
}
