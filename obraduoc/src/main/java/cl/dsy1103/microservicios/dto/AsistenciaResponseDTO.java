package cl.dsy1103.microservicios.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaResponseDTO {
    private Integer numrun_emp;
    private LocalDate fecha;
    private String hora_llegada;
    private String hora_salida;
    private boolean estado;
}
