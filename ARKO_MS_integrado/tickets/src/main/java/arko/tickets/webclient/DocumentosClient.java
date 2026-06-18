package arko.tickets.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Component
public class DocumentosClient {

    private final WebClient webClient;

    public DocumentosClient(@Value("${documentos.url}") String documentosUrl) {
        this.webClient = WebClient.builder().baseUrl(documentosUrl).build();
    }

    public Map<String, Object> obtenerDocumento(Long documentoId, String token) {
        try {
            return this.webClient.get()
                    .uri("/api/documentos/{id}", documentoId)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("El servicio de Documentos no esta disponible en este momento");
        }
    }
}
