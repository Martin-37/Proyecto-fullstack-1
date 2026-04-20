package cl.Arko.asistencia.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.PrivateKey;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Asistencia")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asistencia {

    @Id
    //Extrae el Rut del trabajador para identificarlo, No se usa ID para ecitar posibles errores de identificacion manual.
    @ManyToOne
    @JoinColumn(name = "Empleado", referencedColumnName = "rut", nullable = false )
    private String empleado;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private LocalTime hora_llegada;

    @Column
    private LocalTime hora_salida;

    //Almacena estado para verificar si se termino el dia
    @Column(nullable = false)
    private boolean estado;
}
