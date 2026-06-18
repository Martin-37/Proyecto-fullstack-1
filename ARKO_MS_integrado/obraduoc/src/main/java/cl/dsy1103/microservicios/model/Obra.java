package cl.dsy1103.microservicios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Obra.java
 * Entidad que representa la tabla OBRA del diagrama.
 * PK: nombreObra (clave natural tipo String, igual que en el diagrama).
 * FK: responsable -> TRABAJADOR.numrunTrab (muchas obras pueden tener
 *     el mismo responsable => @ManyToOne).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "obras")
public class Obra {

    // PK natural (nombre unico de la obra)
    @Id
    @Column(name = "nombre_obra", nullable = false, length = 120)
    private String nombreObra;

    @Column(nullable = false, length = 120)
    private String ubicacion;

    // FK a Trabajador: muchas Obras pueden tener el mismo responsable
    @ManyToOne
    @JoinColumn(name = "responsable", nullable = false)
    private Trabajador responsable;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false, length = 30)
    private String estado;
}
