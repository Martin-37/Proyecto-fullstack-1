package cl.maquinaria.maquinariaPesada.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "maquinaria")
public class Maquinaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaquinaria;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String equipo;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false, length = 255)
    private String ubicacion;

    @Column(nullable = false, length = 150)
    private String responsable;

    @Column(nullable = false)
    private LocalDate fechaMantenimiento;

}
