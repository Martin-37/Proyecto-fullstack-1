package arko.comentarios.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Component
public class PersonalClient {

    private final WebClient webClient;

    public PersonalClient(@Value("${personal.url}") String personalUrl) {
        this.webClient = WebClient.builder().baseUrl(personalUrl).build();
    }

    public Map<String, Object> obtenerTrabajador(Long numrunTrab, String token) {
        try {
            return this.webClient.get()
                    .uri("/api/trabajadores/{run}", numrunTrab)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("El servicio de Personal no esta disponible en este momento");
        }
    }
}
