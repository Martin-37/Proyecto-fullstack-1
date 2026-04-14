package cl.Arko.asistencia.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;


@Entity
@Table(name="Empleado")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;

    @Column(nullable = false, unique = true, length = 13)
    private String rut;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

}
