package arko.cl.personal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

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

    // Datos de asistencia obtenidos desde el microservicio de asistencia
    private Map<String, Object> asistenciaInfo;

    public String getNombreCompleto() {
        return pnombreTrab + " " + snombreTrab + " " + papellidoTrab + " " + sapellidoTrab;
    }
}
