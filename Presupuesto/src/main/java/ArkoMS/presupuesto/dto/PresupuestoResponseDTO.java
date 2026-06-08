package ArkoMS.presupuesto.dto;

import ArkoMS.presupuesto.model.ObraTemp;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor   
public class PresupuestoResponseDTO {
    
    private Long id;

    private ObraTemp obra;
    
    private Integer costoMaterial;
    
    private Integer costoManoObra;
    
    private Integer costoTotal;
    
    private String estado;
}
