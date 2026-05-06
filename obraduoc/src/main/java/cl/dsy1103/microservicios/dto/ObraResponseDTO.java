package cl.dsy1103.microservicios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * ObraResponseDTO.java
 * DTO de SALIDA: lo que el servidor devuelve al cliente.
 * Sin anotaciones de validacion: el servidor lo construye, no viene del cliente.
 *
 * En lugar de devolver el objeto Trabajador completo,
 * se exponen solo los datos utiles (nombre completo y cargo).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObraResponseDTO {

    private String nombreObra;
    private String ubicacion;
    // Nombre completo del responsable en lugar del objeto Trabajador
    private String responsableNombre;
    private String responsableCargo;
    private LocalDate fechaInicio;
    private String estado;
}
