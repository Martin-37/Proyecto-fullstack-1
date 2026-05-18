 package cl.Arko.asistencia.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Empleado")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    //Se usa el rut como ID para facilitar la gestion de empleados
    @Id
    @Column(nullable = false, unique = true, length = 8)
    private Integer numrun_emp;

    //Se separa el digito verificador para facilitar la comparacion y extraccion de datos
    @Column(nullable = false)
    private String dvrun_emp;

    //El nombre se separa unicamente para manejo de base de datos
    @Column(nullable = false)
    private String pnombre_emp;

    @Column
    private String snombre_emp;

    @Column(nullable = false)
    private String apaterno_emp;

    @Column(nullable = false)
    private String amaterno_emp;


    @Column(nullable = false)
    private String cargo_emp;

    //Se guarda el numero de telefono en integer usando como base el formato 9 9999 9999, debido a que se limita a residentes de chile
    @Column(nullable = false)
    private Integer fono_emp;

    @Column(nullable = false)
    private String mail_emp;
}
