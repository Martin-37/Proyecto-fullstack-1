package ArkoMS.presupuesto.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "obraTemp")
public class ObraTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "obra_id")
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    // Constructor for (int, String, String) to match DataInitializer usage
    public ObraTemp(int id, String nombre, String descripcion) {
        this.id = (long) id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Constructor for (String, String) without ID
    public ObraTemp(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
