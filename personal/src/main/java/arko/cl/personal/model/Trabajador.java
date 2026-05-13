package arko.cl.personal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trabajador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trabajador {

    @Id
    @Column(nullable = false, unique = true)
    private Long numrunTrab;

    @Column(nullable = false, length = 1)
    private String dvrunTrab;

    @Column(nullable = false, length = 30)
    private String pnombreTrab;

    @Column(nullable = false, length = 30)
    private String snombreTrab;

    @Column(nullable = false, length = 30)
    private String papellidoTrab;

    @Column(nullable = false, length = 30)
    private String sapellidoTrab;

    @Column(nullable = false, length = 30)
    private String cargo;

    @Column(length = 15)
    private String fono;

    @Column(length = 100)
    private String mailTrab;

    @Column(length = 30)
    private String estado;
}
