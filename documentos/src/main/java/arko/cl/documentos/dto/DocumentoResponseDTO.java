package arko.cl.documentos.dto;

import arko.cl.documentos.model.Documento.EstadoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoResponseDTO {

    private Long idDocumento;

    private String nombrePlano;

    private String tipo;

    private LocalDateTime fecha;

    private Long obraAsociada;

    private String rutaDocumento;

    private EstadoDocumento estado;

    private String observaciones;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;
}
