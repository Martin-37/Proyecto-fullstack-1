package ArkoMS.presupuesto.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor   
public class FinanzaResponseDTO {
    
    private Long id;
    
    private Integer gastos;
    
    private Integer ingreso;
    
    private LocalDate fecha;
    
    private String descripcion;
}
