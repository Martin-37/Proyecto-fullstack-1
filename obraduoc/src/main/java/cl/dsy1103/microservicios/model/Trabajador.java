package cl.dsy1103.microservicios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Trabajador.java
 * Entidad que representa la tabla TRABAJADOR.
 * La PK es numrunTrab (RUT del trabajador).
 * Al ser una clave natural, NO usa @GeneratedValue:
 * el valor lo asigna el cliente al crear el registro.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trabajadores")
public class Trabajador {

    @Id
    @Column(name = "numrun_trab", nullable = false)
    private Long numrunTrab;

    @Column(name = "dvrun_trab", nullable = false, length = 1)
    private String dvrunTrab;

    @Column(name = "pnombre_trab", nullable = false, length = 30)
    private String pnombreTrab;

    @Column(name = "snombre_trab", length = 30)
    private String snombreTrab;

    @Column(name = "apaterno_trab", nullable = false, length = 30)
    private String apaternoTrab;

    @Column(name = "amaterno_trab", length = 30)
    private String amaternoTrab;

    @Column(nullable = false, length = 50)
    private String cargo;

    @Column(length = 9)
    private Long fono;

    @Column(name = "mail_trab", length = 100)
    private String mailTrab;
}
