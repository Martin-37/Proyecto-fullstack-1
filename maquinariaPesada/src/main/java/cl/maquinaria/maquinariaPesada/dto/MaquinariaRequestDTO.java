package cl.maquinaria.maquinariaPesada.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de ENTRADA: lo que el cliente envía en POST o PUT.
 *
 * REGLAS:
 * - Las validaciones (@NotBlank, @Size, etc.) viven AQUÍ
 * - NO en la entidad Maquinaria.java
 * - Cuando el Controller usa @Valid, Spring valida este DTO
 * - Si falla, GlobalExceptionHandler captura y devuelve 400
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaquinariaRequestDTO {

    // NO tiene campo "idMaquinaria": MySQL lo genera automáticamente

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @NotBlank(message = "El equipo no puede estar vacío")
    @Size(max = 100, message = "El equipo no puede exceder 100 caracteres")
    private String equipo;

    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(regexp = "^(Operativo|En mantenimiento|Fuera de servicio)$",
             message = "El estado debe ser: Operativo, En mantenimiento o Fuera de servicio")
    private String estado;

    @NotBlank(message = "La ubicación no puede estar vacía")
    @Size(max = 255, message = "La ubicación no puede exceder 255 caracteres")
    private String ubicacion;

    @NotBlank(message = "El responsable no puede estar vacío")
    @Size(max = 150, message = "El responsable no puede exceder 150 caracteres")
    private String responsable;

    @NotNull(message = "La fecha de mantenimiento no puede estar vacía")
    private LocalDate fechaMantenimiento;
}
