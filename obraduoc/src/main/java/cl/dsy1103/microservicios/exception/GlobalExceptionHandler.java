package cl.dsy1103.microservicios.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler.java
 * Por que existe este archivo?
 *   Sin el, cuando @Valid falla Spring devuelve un JSON enorme e ilegible.
 *   Con el, el cliente recibe respuestas limpias:
 *   {
 *     "nombreObra": "El nombre de la obra no puede estar vacio",
 *     "estado":     "El estado no puede estar vacio"
 *   }
 *
 * @RestControllerAdvice: marca esta clase como manejador global de excepciones
 *   para todos los Controllers. Spring lo detecta automaticamente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ERROR DE VALIDACION (@Valid)
     * Se dispara cuando un campo del RequestDTO no cumple
     * las restricciones (@NotBlank, @NotNull, etc.).
     * Spring lanza MethodArgumentNotValidException.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        // LinkedHashMap mantiene el orden de insercion
        Map<String, String> errores = new LinkedHashMap<>();
        // getFieldErrors() devuelve uno por cada campo invalido.
        // getField()          -> nombre del campo del DTO ("nombreObra", "estado"...)
        // getDefaultMessage() -> el texto del message= en la anotacion
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));
        // 400 Bad Request: el cliente envio datos invalidos.
        return ResponseEntity.badRequest().body(errores);
    }

    /**
     * ERROR DE NEGOCIO (trabajador no encontrado, etc.)
     * Se dispara cuando el Service lanza RuntimeException,
     * por ejemplo: "Trabajador no encontrado con numrun: 99"
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(
            RuntimeException ex) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", ex.getMessage());
        // 400 Bad Request: el cliente envio un dato que no existe.
        // Usamos 400 y no 500 porque el servidor funciono correctamente;
        // fue el dato enviado el que causo el problema.
        return ResponseEntity.badRequest().body(error);
    }
}
