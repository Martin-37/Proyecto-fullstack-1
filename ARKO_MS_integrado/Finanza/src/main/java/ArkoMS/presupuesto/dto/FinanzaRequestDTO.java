package ArkoMS.presupuesto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanzaRequestDTO {

    @NotNull(message = "Los gastos no pueden ser nulos")
    private Integer gastos;

    @NotNull(message = "Los ingresos no pueden ser nulos")
    private Integer ingreso;

    @NotNull(message = "La fecha no puede ser nula")
    private LocalDate fecha;

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;

}
