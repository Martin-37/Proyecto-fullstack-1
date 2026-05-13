package arko.cl.personal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorResponseDTO {

    private Long numrunTrab;
    private String dvrunTrab;
    private String pnombreTrab;
    private String snombreTrab;
    private String papellidoTrab;
    private String sapellidoTrab;
    private String cargo;
    private String fono;
    private String mailTrab;
    private String estado;

    public String getNombreCompleto() {
        return pnombreTrab + " " + snombreTrab + " " + papellidoTrab + " " + sapellidoTrab;
    }
}
