package arko.comentarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioResponseDTO {

    private Long id;
    private Long ticketId;
    private Long autorRun;
    private String mensaje;
    private LocalDateTime fecha;
}
