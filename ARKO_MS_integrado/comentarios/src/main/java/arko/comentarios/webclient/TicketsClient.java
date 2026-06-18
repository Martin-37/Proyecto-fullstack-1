package arko.comentarios.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Component
public class TicketsClient {

    private final WebClient webClient;

    public TicketsClient(@Value("${tickets.url}") String ticketsUrl) {
        this.webClient = WebClient.builder().baseUrl(ticketsUrl).build();
    }

    public Map<String, Object> obtenerTicket(Long ticketId, String token) {
        return get("/api/tickets/" + ticketId, token);
    }

    public Map<String, Object> obtenerTicketConDocumento(Long ticketId, String token) {
        return get("/api/tickets/" + ticketId + "/con-documento", token);
    }

    private Map<String, Object> get(String uri, String token) {
        try {
            return this.webClient.get()
                    .uri(uri)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("El servicio de Tickets no esta disponible en este momento");
        }
    }
}
