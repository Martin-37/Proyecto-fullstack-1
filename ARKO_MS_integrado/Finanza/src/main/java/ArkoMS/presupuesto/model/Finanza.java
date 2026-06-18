package ArkoMS.presupuesto.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "finanza")
public class Finanza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gastos")
    private Integer gastos;

    @Column(name = "ingreso")
    private Integer ingreso;

    // LocalDate no tiene componente de hora/zona horaria, por lo que
    // se almacena y compara como una fecha pura (sin milisegundos),
    // evitando el bug de comparaciones que fallaban con java.util.Date.
    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "descripcion")
    private String descripcion;

}
