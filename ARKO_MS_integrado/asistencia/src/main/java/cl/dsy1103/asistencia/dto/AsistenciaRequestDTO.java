package cl.dsy1103.asistencia.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsistenciaRequestDTO {

    @NotBlank(message = "El número de RUN del empleado es obligatorio")
    private Integer numrun_emp;

    @NotBlank(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora de llegada es obligatoria")
    private LocalTime hora_llegada;

    //Debido a que la hora de salida se marca después, no se puede validar como NotNull, se valida en el servicio para evitar errores.
    private LocalTime hora_salida;
    
    @NotNull(message = "El estado es obligatorio")
    private boolean estado;
}
