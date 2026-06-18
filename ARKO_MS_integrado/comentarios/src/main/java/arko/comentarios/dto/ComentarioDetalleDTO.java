package arko.comentarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDetalleDTO {

    private Long id;
    private Long ticketId;
    private Long autorRun;
    private String mensaje;
    private LocalDateTime fecha;
    private Map<String, Object> ticket;
    private Map<String, Object> autor;
}
