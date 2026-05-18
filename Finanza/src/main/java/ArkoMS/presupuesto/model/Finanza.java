package ArkoMS.presupuesto.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "presupuesto")
public class Finanza {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "costo_material", nullable = false)
    private Integer costoMaterial;

    @Column(name = "costo_mano_obra", nullable = false) 
    private Integer costoManoObra;
    
    @Column(name = "costo_total")
    private Integer costoTotal;
    
    //Determina si ya se ha aprobado el presupuesto o no, para que no se pueda modificar
    @Column(name = "estado_pres", nullable = false)
    private String estado;
}
