package arko.tickets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDTO {

    @NotBlank(message = "El titulo no puede estar vacío")
    @Size(min = 3, max = 200, message = "El titulo debe tener entre 3 y 200 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 5000, message = "La descripción debe tener entre 10 y 5000 caracteres")
    private String descripcion;

    @NotBlank(message = "La prioridad no puede estar vacía")
    private String prioridad;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    @NotBlank(message = "El responsable no puede estar vacío")
    @Size(min = 2, max = 100, message = "El responsable debe tener entre 2 y 100 caracteres")
    private String responsable;
}
