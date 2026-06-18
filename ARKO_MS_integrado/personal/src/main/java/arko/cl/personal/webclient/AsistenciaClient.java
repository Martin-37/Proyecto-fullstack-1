package arko.cl.personal.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class AsistenciaClient {

    private final WebClient webClient;

    public AsistenciaClient(@Value("${asistencia.url}") String asistenciaUrl) {
        this.webClient = WebClient.builder().baseUrl(asistenciaUrl).build();
    }

    public Map<String, Object> obtenerAsistenciaByTrabajador(Long numrunTrab) {
        try {
            return this.webClient.get()
                    .uri("/trabajador/{numrunTrab}", numrunTrab)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(),
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("Asistencia no encontrada para trabajador: " + numrunTrab)))
                    .bodyToMono(Map.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public long obtenerDiasTrabajadasByTrabajador(Long numrunTrab) {
        try {
            Map<String, Object> asistencia = obtenerAsistenciaByTrabajador(numrunTrab);
            if (asistencia != null && asistencia.containsKey("diasTrabajados")) {
                return ((Number) asistencia.get("diasTrabajados")).longValue();
            }
        } catch (Exception e) {
            return 0L;
        }
        return 0L;
    }
}
