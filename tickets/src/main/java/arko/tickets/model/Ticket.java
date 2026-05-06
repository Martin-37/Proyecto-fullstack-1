package arko.tickets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String descripcion;

    @Column(nullable = false, length = 50)
    private String prioridad;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false, length = 100)
    private String responsable;
}
