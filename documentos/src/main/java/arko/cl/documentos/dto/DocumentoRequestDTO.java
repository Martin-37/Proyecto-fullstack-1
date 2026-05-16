package arko.cl.documentos.dto;

import arko.cl.documentos.model.Documento.EstadoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoRequestDTO {

    @NotBlank(message = "Nombre del plano es obligatorio")
    @Size(min = 3, max = 150, message = "Nombre debe tener entre 3 y 150 caracteres")
    private String nombrePlano;

    @NotBlank(message = "Tipo de documento es obligatorio")
    @Size(max = 50, message = "Tipo no puede exceder 50 caracteres")
    private String tipo;

    @NotNull(message = "Fecha es obligatoria")
    private LocalDateTime fecha;

    @NotNull(message = "ID de obra asociada es obligatorio")
    @Positive(message = "ID de obra debe ser positivo")
    private Long obraAsociada;

    @NotBlank(message = "Ruta o URL del documento es obligatoria")
    @Size(max = 500, message = "Ruta no puede exceder 500 caracteres")
    private String rutaDocumento;

    @NotNull(message = "Estado es obligatorio")
    private EstadoDocumento estado;

    @Size(max = 500, message = "Observaciones no pueden exceder 500 caracteres")
    private String observaciones;
}
