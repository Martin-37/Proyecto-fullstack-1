package ArkoMS.presupuesto.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor   
public class FinanzaResponseDTO {
    
    private Long id;
    
    private Integer costoMaterial;
    
    private Integer costoManoObra;
    
    private Integer costoTotal;
    
    private String estado;
}
