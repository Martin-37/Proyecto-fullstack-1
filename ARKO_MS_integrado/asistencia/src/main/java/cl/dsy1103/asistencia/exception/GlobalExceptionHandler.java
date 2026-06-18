package cl.dsy1103.asistencia.exception;

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
 *     "numrun_emp": "El número de RUN del empleado es obligatorio"
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
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }

    /**
     * ERROR DE NEGOCIO (registro de entrada no encontrado, etc.)
     * Se dispara cuando el Service lanza RuntimeException,
     * por ejemplo: "No se encontró registro de entrada para este usuario hoy."
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(
            RuntimeException ex) {
        Map<String, String> error = new LinkedHashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
