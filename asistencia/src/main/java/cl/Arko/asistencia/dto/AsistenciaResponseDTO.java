package cl.Arko.asistencia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaResponseDTO {
    private Integer numrun_emp;
    private String fecha;
    private String hora_llegada;
    private String hora_salida;
    private boolean estado;
}
