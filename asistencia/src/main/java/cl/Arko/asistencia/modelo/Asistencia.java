package cl.Arko.asistencia.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.PrivateKey;

@Entity
@Table(name = "Asistencia")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asistencia {

    @Autowired
    private Empleado empleado;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAs;

    @Column(nullable = false)
    @ForeignKey(name = "FK_ASIS_EMP")
    private String rutEmp;

}
