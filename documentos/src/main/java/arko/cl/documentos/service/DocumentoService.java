package arko.cl.documentos.service;

import arko.cl.documentos.dto.DocumentoRequestDTO;
import arko.cl.documentos.dto.DocumentoResponseDTO;
import arko.cl.documentos.model.Documento;
import arko.cl.documentos.model.Documento.EstadoDocumento;
import arko.cl.documentos.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository documentoRepository;

    public DocumentoResponseDTO crearDocumento(DocumentoRequestDTO request) {
        Documento documento = Documento.builder()
            .nombrePlano(request.getNombrePlano())
            .tipo(request.getTipo())
            .fecha(request.getFecha())
            .obraAsociada(request.getObraAsociada())
            .rutaDocumento(request.getRutaDocumento())
            .estado(request.getEstado())
            .observaciones(request.getObservaciones())
            .build();

        Documento guardado = documentoRepository.save(documento);
        return mapToDTO(guardado);
    }

    public DocumentoResponseDTO obtenerDocumento(Long idDocumento) {
        Documento documento = documentoRepository.findById(idDocumento)
            .orElseThrow(() -> new RuntimeException("Documento no encontrado con id: " + idDocumento));
        return mapToDTO(documento);
    }

    public List<DocumentoResponseDTO> listarTodos() {
        return documentoRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public List<DocumentoResponseDTO> documentosPorObra(Long obraAsociada) {
        return documentoRepository.findByObraAsociada(obraAsociada).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public List<DocumentoResponseDTO> documentosPorTipo(String tipo) {
        return documentoRepository.findByTipo(tipo).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public List<DocumentoResponseDTO> documentosPorEstado(EstadoDocumento estado) {
        return documentoRepository.findByEstado(estado).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public List<DocumentoResponseDTO> buscarPorNombre(String nombrePlano) {
        return documentoRepository.findByNombrePlanoContains(nombrePlano).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public DocumentoResponseDTO actualizarDocumento(Long idDocumento, DocumentoRequestDTO request) {
        Documento documento = documentoRepository.findById(idDocumento)
            .orElseThrow(() -> new RuntimeException("Documento no encontrado con id: " + idDocumento));

        documento.setNombrePlano(request.getNombrePlano());
        documento.setTipo(request.getTipo());
        documento.setFecha(request.getFecha());
        documento.setObraAsociada(request.getObraAsociada());
        documento.setRutaDocumento(request.getRutaDocumento());
        documento.setEstado(request.getEstado());
        documento.setObservaciones(request.getObservaciones());

        Documento actualizado = documentoRepository.save(documento);
        return mapToDTO(actualizado);
    }

    public void eliminarDocumento(Long idDocumento) {
        if (!documentoRepository.existsById(idDocumento)) {
            throw new RuntimeException("Documento no encontrado con id: " + idDocumento);
        }
        documentoRepository.deleteById(idDocumento);
    }

    public long contarPorObra(Long obraAsociada) {
        return documentoRepository.countByObraAsociada(obraAsociada);
    }

    public long contarPorEstado(EstadoDocumento estado) {
        return documentoRepository.countByEstado(estado);
    }

    private DocumentoResponseDTO mapToDTO(Documento documento) {
        DocumentoResponseDTO dto = new DocumentoResponseDTO();
        dto.setIdDocumento(documento.getIdDocumento());
        dto.setNombrePlano(documento.getNombrePlano());
        dto.setTipo(documento.getTipo());
        dto.setFecha(documento.getFecha());
        dto.setObraAsociada(documento.getObraAsociada());
        dto.setRutaDocumento(documento.getRutaDocumento());
        dto.setEstado(documento.getEstado());
        dto.setObservaciones(documento.getObservaciones());
        dto.setFechaCreacion(documento.getFechaCreacion());
        dto.setFechaActualizacion(documento.getFechaActualizacion());
        return dto;
    }
}
