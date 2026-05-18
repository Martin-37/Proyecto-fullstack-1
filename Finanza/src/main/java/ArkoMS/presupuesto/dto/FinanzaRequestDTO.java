package ArkoMS.presupuesto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanzaRequestDTO {


    @NotNull(message = "El costo del material no puede ser nulo")
    private Integer costoMaterial;

    @NotNull(message = "El costo de la mano de obra no puede ser nulo")
    private Integer costoManoObra;

    @NotNull(message = "El costo total no puede ser nulo")
    private Integer costoTotal;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estado;
}
