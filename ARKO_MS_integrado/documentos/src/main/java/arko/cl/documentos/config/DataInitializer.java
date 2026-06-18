package arko.cl.documentos.config;

import arko.cl.documentos.model.Documento;
import arko.cl.documentos.model.Documento.EstadoDocumento;
import arko.cl.documentos.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DocumentoRepository documentoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (documentoRepository.count() == 0) {
            documentoRepository.saveAll(Arrays.asList(
                Documento.builder()
                    .nombrePlano("Plano Arquitectónico Obra 1")
                    .tipo("PDF")
                    .fecha(LocalDateTime.now().minusMonths(2))
                    .obraAsociada(1L)
                    .rutaDocumento("https://storage.example.com/obra1/plano-arq.pdf")
                    .estado(EstadoDocumento.VIGENTE)
                    .observaciones("Plano principal de arquitectura")
                    .build(),

                Documento.builder()
                    .nombrePlano("Plano CAD Estructura")
                    .tipo("DWG")
                    .fecha(LocalDateTime.now().minusMonths(3))
                    .obraAsociada(1L)
                    .rutaDocumento("https://storage.example.com/obra1/estructura.dwg")
                    .estado(EstadoDocumento.VIGENTE)
                    .observaciones("Estructura de acero")
                    .build(),

                Documento.builder()
                    .nombrePlano("Plano Instalaciones Eléctricas")
                    .tipo("PDF")
                    .fecha(LocalDateTime.now().minusMonths(1))
                    .obraAsociada(1L)
                    .rutaDocumento("https://storage.example.com/obra1/electricidad.pdf")
                    .estado(EstadoDocumento.VIGENTE)
                    .observaciones("Red eléctrica completa")
                    .build(),

                Documento.builder()
                    .nombrePlano("Plano Tuberías")
                    .tipo("CAD")
                    .fecha(LocalDateTime.now().minusMonths(2))
                    .obraAsociada(2L)
                    .rutaDocumento("https://storage.example.com/obra2/tuberias.cad")
                    .estado(EstadoDocumento.VIGENTE)
                    .observaciones("Sistema de agua y desagüe")
                    .build(),

                Documento.builder()
                    .nombrePlano("Presupuesto Obra 2")
                    .tipo("EXCEL")
                    .fecha(LocalDateTime.now().minusMonths(4))
                    .obraAsociada(2L)
                    .rutaDocumento("https://drive.google.com/file/d/1abc123/presupuesto")
                    .estado(EstadoDocumento.OBSOLETO)
                    .observaciones("Presupuesto versión anterior")
                    .build(),

                Documento.builder()
                    .nombrePlano("Cronograma Obra 2")
                    .tipo("PDF")
                    .fecha(LocalDateTime.now())
                    .obraAsociada(2L)
                    .rutaDocumento("https://storage.example.com/obra2/cronograma-v2.pdf")
                    .estado(EstadoDocumento.VIGENTE)
                    .observaciones("Cronograma actualizado al mes")
                    .build()
            ));

            System.out.println("✅ DataInitializer: 6 documentos cargados");
        }
    }
}
