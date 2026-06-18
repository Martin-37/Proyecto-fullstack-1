package arko.comentarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioRequestDTO {

    @NotNull(message = "El ticketId no puede ser nulo")
    private Long ticketId;

    @NotNull(message = "El autorRun no puede ser nulo")
    private Long autorRun;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(min = 1, max = 1000, message = "El mensaje debe tener entre 1 y 1000 caracteres")
    private String mensaje;
}
